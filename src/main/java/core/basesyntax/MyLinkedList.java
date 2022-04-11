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
        Node<T> counter = getElementByIndex(index);
        T result = counter.value;
        counter.value = value;
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
        Node<T> counter = getElementByIndex(index);
        if (counter.prev == null) {
            result = counter.value;
            head = counter.next;
            counter.next.prev = null;
            counter.next = null;
            size--;
            return result;
        }
        if (counter.next == null) {
            result = counter.value;
            tail = counter.prev;
            counter.prev.next = null;
            counter.prev = null;
            size--;
            return result;
        }
        result = counter.value;
        counter.prev.next = counter.next;
        counter.next.prev = counter.prev;
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
        Node<T> counter = head;
        while (counter != null) {
            if (counter.value == null && counter.value == object || counter.value.equals(object)) {
                if (counter.prev == null) {
                    head = counter.next;
                    counter.next.prev = null;
                    counter.next = null;
                    size--;
                    return true;
                }
                if (counter.next == null) {
                    tail = counter.prev;
                    counter.prev.next = null;
                    counter.prev = null;
                    size--;
                    return true;
                }
                counter.prev.next = counter.next;
                counter.next.prev = counter.prev;
                size--;
                return true;
            }
            counter = counter.next;
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
            Node<T> counter = head;
            int count = 0;
            while (counter != null) {
                if (count == index) {
                    return counter;
                }
                count++;
                counter = counter.next;
            }
        } else {
            Node<T> counter = tail;
            int count = size - 1;
            while (counter != null) {
                if (count == index) {
                    return counter;
                }
                count--;
                counter = counter.prev;
            }
        }
        throw new RuntimeException("Value non exists");
    }
}
