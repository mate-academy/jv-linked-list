package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private Node<T> node;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node current = new Node(value);
        if (size == 0) {
            node = current;
            head = node;
            tail = node;
            size++;
        } else {
            node.next = current;
            current.prev = node;
            tail = current;
            node = node.next;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node current = new Node(value);
        if (size == 0) {
            node = current;
            head = node;
            tail = node;
            size++;
            return;
        }
        if (index == 0) {
            head.prev = current;
            current.next = head;
            head = current;
            size++;
            return;
        }
        if (index == size) {
            node.next = current;
            current.prev = node;
            tail = current;
            node = node.next;
            size++;
        } else {
            Node<T> counter = getElementByIndex(index);
            current.prev = counter.prev;
            counter.prev.next = current;
            counter.prev = current;
            current.next = counter;
            size++;
            return;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return getElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = getElementByIndex(index);
        T result = current.value;
        current.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        T result;
        if (index == 0 && size == 1) {
            result = head.value;
            head = null;
            tail = null;
            node = null;
            size--;
            return result;
        }
        Node<T> current = getElementByIndex(index);
        if (current.prev == null) {
            result = current.value;
            head = current.next;
            current.next.prev = null;
            current.next = null;
            size--;
            return result;
        }
        if (current.next == null) {
            result = current.value;
            tail = current.prev;
            current.prev.next = null;
            current.prev = null;
            size--;
            return result;
        }
        result = current.value;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) {
        if (size == 1 && head.value == object) {
            head = null;
            tail = null;
            node = null;
            size--;
            return true;
        }
        Node<T> current = head;
        while (current != null) {
            if (current.value == null && current.value == object || current.value.equals(object)) {
                if (current.prev == null) {
                    head = current.next;
                    current.next.prev = null;
                    current.next = null;
                    size--;
                    return true;
                }
                if (current.next == null) {
                    tail = current.prev;
                    current.prev.next = null;
                    current.prev = null;
                    size--;
                    return true;
                }
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    private Node<T> getElementByIndex(int index) {
        if (index < size / 2) {
            Node<T> current = head;
            int count = 0;
            while (current != null) {
                if (count == index) {
                    return current;
                }
                count++;
                current = current.next;
            }
        } else {
            Node<T> current = tail;
            int count = size - 1;
            while (current != null) {
                if (count == index) {
                    return current;
                }
                count--;
                current = current.prev;
            }
        }
        throw new RuntimeException("Value non exists");
    }
}
