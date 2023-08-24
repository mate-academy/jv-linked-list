package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        if (index == size) {
            linkLast(value);
        } else if (index == 0) {
            linkFirst(value);
        } else {
            link(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
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
        T oldValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(index);
    }

    @Override
    public boolean remove(T object) {
        int removeByIndex = getIndexIfContains(object);
        if (removeByIndex == -1) {
            return false;
        }
        unlink(removeByIndex);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    private void linkFirst(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void link(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> previous = getNodeByIndex(index - 1);
        newNode.next = previous.next;
        previous.next.prev = newNode;
        previous.next = newNode;
        newNode.prev = previous;
        size++;
    }

    private int getIndexIfContains(T object) {
        int index = -1;
        Node<T> current = head;
        int iteration = 0;
        while (current != null) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
                return iteration;
            }
            current = current.next;
            iteration++;
        }
        return index;
    }

    private T unlink(int index) {
        if (size == 1) {
            return unlinkTheOne();
        } else if (index == 0) {
            return unlinkFirst();
        } else if (index == size - 1) {
            return unlinkLast();
        } else {
            return unlinkWithin(index);
        }
    }

    private T unlinkTheOne() {
        T removedValue;
        removedValue = head.value;
        head = tail = null;
        size--;
        return removedValue;
    }

    private T unlinkFirst() {
        T removedValue;
        removedValue = head.value;
        head = head.next;
        head.prev = null;
        size--;
        return removedValue;
    }

    private T unlinkLast() {
        T removedValue;
        removedValue = tail.value;
        tail.prev.next = null;
        tail = tail.prev;
        size--;
        return removedValue;
    }

    private T unlinkWithin(int index) {
        T removedValue;
        Node<T> previous = getNodeByIndex(index - 1);
        removedValue = previous.next.value;
        previous.next = previous.next.next;
        previous.next.prev = previous;
        size--;
        return removedValue;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index > size / 2) {
            return searchFromHead(index);
        } else {
            return searchFromTail(index);
        }
    }

    private Node<T> searchFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
        }
    }
}

