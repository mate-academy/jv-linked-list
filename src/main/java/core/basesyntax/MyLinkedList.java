package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev , T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        if (index == size) {
            add(value);
            return;
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> current = node(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = node(index);
        T oldItem = current.item;
        current.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = node(index);
        T remove = current.item;
        if (index == 0) {
            head = current.next;
            current.next.prev = null;
            current.next = null;
        } else if (index == size - 1) {
            tail = current.prev;
            current.prev.next = null;
            current.prev = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current.next = null;
            current.prev = null;
        }
        current.item = null;
        size--;
        return remove;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> node(int index) {
        Node<T> current;
        if (size / 2 >= index) {
            Node<T> previous = null;
            current = head;
            for (int i = 0; i < index; i++){
                previous = current;
                current = current.next;
            }
        } else {
            Node<T> after = null;
            current = tail;
            for (int i = size; i > index + 1; i--) {//
                after = current;
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
    }
}
