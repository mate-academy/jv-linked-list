package core.basesyntax;

import java.util.List;
import java.util.Objects;

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
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
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
        }
        Node<T> current = findNode(index);
        Node<T> prev = current.prev;
        Node<T> newNode = new Node<>(prev, value, current);
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        current.prev = newNode;
        size++;
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
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNode(index);
        T oldItem = current.item;
        current.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = findNode(index);
        T remove = current.item;
        unlinkNode(current);
        return remove;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        for (Node<T> i = head; i != null; i = i.next) {
            if (Objects.equals(i.item, object)) {
                unlinkNode(findNode(index));
                return true;
            }
            index++;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findNode(int index) {
        Node<T> current;
        if (size / 2 >= index) {
            Node<T> previous = null;
            current = head;
            for (int i = 0; i < index; i++) {
                previous = current;
                current = current.next;
            }
        } else {
            Node<T> after = null;
            current = tail;
            for (int i = size; i > index + 1; i--) {
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

    private void unlinkNode(Node<T> current) {
        Node<T> prev = current.prev;
        Node<T> next = current.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            current.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            current.next = null;
        }
        size--;
    }
}
