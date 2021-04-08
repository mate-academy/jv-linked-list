package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        Node(T value) {
            this.value = value;
        }

        Node(Node<T> previous, Node<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }

    }

    public MyLinkedList() {

    }

    @Override
    public boolean add(T value) {
        addTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if (index == size) {
            addTail(value);
        } else {
            addByIndex(value, index);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = getCurrentNode(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue;
        Node<T> current = getCurrentNode(index);
        oldValue = current.value;

        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getCurrentNode(index);
        Node<T> next = current.next;
        Node<T> previous = current.previous;
        final T deletedValue = current.value;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            current.previous = null;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            current.next = null;
        }

        current.value = null;
        size--;
        return deletedValue;
    }

    @Override
    public boolean remove(T object) {
        int indexOfObject = findIndexByValue(object);
        if (indexOfObject != -1) {
            remove(indexOfObject);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private void checkIndex(int index) {
        if (!isIndexInRange(index)) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
    }

    private boolean isIndexInRange(int index) {
        return index >= 0 && index < size;
    }

    private Node<T> getCurrentNode(int index) {
        Node<T> current = head;
        int i = 0;

        while (i != index) {
            current = current.next;
            i++;
        }
        return current;
    }

    private void addTail(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.previous = tail;
        tail = newNode;

        if (newNode.previous == null) {
            head = newNode;
        } else {
            (newNode.previous).next = newNode;
        }
        size++;
    }

    private void addByIndex(T value, int index) {
        Node<T> currentNode = getCurrentNode(index);
        Node<T> previous = currentNode.previous;
        Node<T> newNode = new Node<>(previous, currentNode, value);
        currentNode.previous = newNode;

        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private int findIndexByValue(T value) {
        int i = 0;
        Node<T> current = head;

        while (i < size) {
            if (current.value == value
                    || value != null && value.equals(current.value)) {
                return i;
            }
            current = current.next;
            i++;
        }
        return -1;
    }
}
