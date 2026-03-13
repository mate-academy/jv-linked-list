package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node {
        T value;
        Node prev;
        Node next;

        Node(T value) {
            this.value = value;
        }
    }

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

    public void addFirst(T value) {
        Node node = new Node(value);
        Node current = head;
        node.prev = null;
        node.next = current;
        current.prev = node;
        head = node;
        size++;
    }

    public Node getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
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

    @Override
    public T remove(int index) {
        Node current = getNodeByIndex(index);
        T oldValue = current.value;
        Node currentPrev = current.prev;
        Node currentNext = current.next;
        if (currentNext != null && currentPrev != null) {
            currentPrev.next = currentNext;
            currentNext.prev = currentPrev;
        }
        if (currentPrev == null) {
            head = currentNext;
        }
        if (currentNext == null) {
            tail = currentPrev;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        int count = 0;
        if (object == null) {
            while (current != null) {
                if (current.value == null) {
                    remove(count);
                    return true;
                }
                current = current.next;
                count++;
            }
        }
        while (current != null) {
            if (current.value != null && current.value.equals(object)) {
                remove(count);
                return true;
            }
            current = current.next;
            count++;
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
}
