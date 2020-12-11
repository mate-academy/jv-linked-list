package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> tmp = tail;
            tail = new Node<>(tmp, value, null);
            tmp.next = tail;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        if (index == size || head == null) {
            add(value);
            return;
        }
        Node<T> current = getNode(index);
        Node<T> previous = current.prev;
        Node<T> element = new Node<>(previous, value, current);
        if (previous != null) {
            previous.next = element;
        }
        if (previous == null) {
            head = element;
        }
        current.prev = element;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }
        Node<T> current = getNode(index);
        current.next.prev = current.prev;
        current.prev.next = current.next;
        current.next = null;
        current.prev = null;
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        int index = nodeIndex(object);
        if (index == size) {
            return false;
        }
        remove(index);
        return true;
    }

    private T removeFirst() {
        Node<T> tmp = head;
        if (head.equals(tail)) {
            head = null;
            size--;
            return tmp.item;
        }
        head = tmp.next;
        tmp.next.prev = null;
        tmp.next = null;
        size--;
        return tmp.item;
    }

    private T removeLast() {
        Node<T> tmp = tail;
        tail = tmp.prev;
        tmp.prev.next = null;
        tmp.prev = null;
        size--;
        return tmp.item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        Node<T> current = head;
        int count = 0;
        while (current != null && count != index) {
            current = current.next;
            count++;
        }
        return current;
    }

    private int nodeIndex(T element) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (current.item == null && element == null
                    || current.item != null && current.item.equals(element)) {
                break;
            }
            index++;
            current = current.next;
        }
        return index;
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }
}
