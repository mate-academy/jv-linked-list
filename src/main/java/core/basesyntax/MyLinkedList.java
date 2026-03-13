package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            Node node = new Node(value);
            head = node;
            tail = node;
            node.next = null;
            node.prev = null;
        } else {
            Node node = new Node(value);
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node node = new Node(value);
        if (index == 0) {
            addFirst(value);
            return;
        }
        Node current = getNodeByIndex(index);
        Node currentPrev = current.prev;
        currentPrev.next = node;
        node.prev = currentPrev;
        node.next = current;
        current.prev = node;
        size++;
    }

    private Node getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
        if (index < size / 2) {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elements : list) {
            add(elements);
        }
    }

    @Override
    public T get(int index) {
        Node current = getNodeByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        Node current = getNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    private void unlink(Node node) {
        Node currentPrev = node.prev;
        Node currentNext = node.next;
        if (currentPrev == null && currentNext == null) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (currentNext != null && currentPrev != null) {
            currentPrev.next = currentNext;
            currentNext.prev = currentPrev;
        }
        if (currentPrev == null) {
            head = currentNext;
            currentNext.prev = null;
        }
        if (currentNext == null) {
            tail = currentPrev;
            currentPrev.next = null;
        }
        size--;
    }

    @Override
    public T remove(int index) {
        Node current = getNodeByIndex(index);
        unlink(current);
        T oldValue = current.value;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        if (object == null) {
            while (current != null) {
                if (current.value == null) {
                    unlink(current);
                    return true;
                }
                current = current.next;
            }
        }
        while (current != null) {
            if (current.value != null && current.value.equals(object)) {
                unlink(current);
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
        return size == 0;
    }

    private void addFirst(T value) {
        Node node = new Node(value);
        Node current = head;
        node.prev = null;
        node.next = current;
        current.prev = node;
        head = node;
        size++;
    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        Node(T value) {
            this.value = value;
        }
    }
}
