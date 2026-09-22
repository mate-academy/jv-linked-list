package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node head;
    private Node tail;

    private Node nodeAt(int index) {
        Node nodeCurrent = head;
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
        for (int i = 0; i < index; i++) {
            nodeCurrent = nodeCurrent.next;
        }
        return nodeCurrent;
    }

    private T unlink(Node current) {
        T removedValue = current.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else if (current == head) {
            head = current.next;
            head.prev = null;
        } else if (current == tail) {
            tail = current.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return removedValue;
    }

    @Override
    public void add(T value) {
        Node node = new Node(value);
        if (size == 0) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node current = head;
        Node node = new Node(value);
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
        if (size == 0) {
            add(value);
        } else if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            node.prev = current.prev;
            node.next = current;
            node.prev.next = node;
            current.prev = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List must not be null");
        }
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return nodeAt(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node current = nodeAt(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(nodeAt(index));
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        while (current != null) {
            if (current.value == object
                    || (current.value != null && current.value.equals(object))) {
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

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        private Node(T value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }
}
