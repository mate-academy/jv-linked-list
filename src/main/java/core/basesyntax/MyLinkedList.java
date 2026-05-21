package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {

        private T value;
        private Node next;
        private Node prev;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node valyByIndex(int index) {
        if (head == null || index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node curent;
        if (index > size / 2) {
            curent = head;
            for (int i = 0; i < index; i++) {
                curent = curent.next;
            }
        } else {
            curent = tail;
            for (int i = size - 1; i > index; i--) {
                curent = curent.prev;
            }
        }
        return curent;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            Node newNode = new Node(value);
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        tail.next = new Node(value);
        tail.next.prev = tail;
        tail = tail.next;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (head == null && index == 0) {
            add(value);
            return;
        }

        if (index == size) {
            tail.next = new Node(value);
            tail.next.prev = tail;
            tail = tail.next;
            size++;
            return;
        }

        if (index == 0) {
            head.prev = new Node(value);
            head.prev.next = head;
            head = head.prev;
            size++;
            return;
        }

        Node current = valyByIndex(index);

        Node newValue = new Node(value);
        newValue.prev = current.prev;
        current.prev.next = newValue;
        newValue.next = current;
        current.prev = newValue;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list.isEmpty()) {
            return;
        }
        if (head == null) {
            head = new Node(list.get(0));
            tail = head;
            size++;
            for (int i = 1; i < list.size(); i++) {
                add(list.get(i));
            }
        } else {
            for (T t : list) {
                add(t);
            }
        }
    }

    @Override
    public T get(int index) {

        if (this.isEmpty() || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node value = head;
        for (int i = 0; i < index; i++) {
            value = value.next;
            if (value == null) {
                throw new IndexOutOfBoundsException();
            }
        }

        return value.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || head == null || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node current = valyByIndex(index);

        T oldValue = current.value;
        current.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {

        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node curent = valyByIndex(index);
        if (curent == null) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return curent.value;
        }

        if (index == 0) {
            head = head.next;
            head.prev = null;
            size--;
            return curent.value;
        }

        if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
            size--;
            return curent.value;
        }

        curent.prev.next = curent.next;
        curent.next.prev = curent.prev;
        size--;
        return curent.value;
    }

    @Override
    public boolean remove(T object) {

        Node current = head;
        while (current != null) {
            if (object == current.value || current.value != null
                    && current.value.equals(object)) {
                break;
            }
            current = current.next;
        }
        if (current == null) {
            return false;
        }

        if (current.next == null && current.prev == null) {
            head = null;
            tail = null;
            size--;
            return true;
        }

        if (current.next == null) {
            tail = tail.prev;
            tail.next = null;
            size--;
            return true;
        }

        if (current.prev == null) {
            head = head.next;
            head.prev = null;
            size--;
            return true;
        }

        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return true;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (head == null);
    }
}
