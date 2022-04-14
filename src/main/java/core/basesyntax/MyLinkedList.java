package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public void checkIndex(int index) throws RuntimeException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index doesn't exist");
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index doesn't exist");
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> oldNode = getNodeByIndex(index);
            newNode.prev = oldNode.prev;
            newNode.next = oldNode;
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        T returnedValue = newNode.value;
        newNode.value = value;
        return returnedValue;
    }

    public void unlinkNode(Node<T> removed) {
        if (removed.prev == null) {
            head = removed.next;
        } else if (removed.next == null) {
            tail = removed.prev;
        } else {
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        }
        size--;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = getNodeByIndex(index);
        unlinkNode(removed);
        return removed.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
                unlinkNode(current);
                return true;
            } else {
                current = current.next;
            }
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

    public Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            current = tail;
            for (int k = size - 1; k > index; k--) {
                current = current.prev;
            }
        }
        return current;
    }
}
