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
        for (T value : list) {
            add(value);
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
        Node<T> current = getCurrentNode(index);
        T oldValue = current.value;

        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getCurrentNode(index);
        final T deletedValue = current.value;
        unlink(current);
        return deletedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == object
                    || object != null && object.equals(current.value)) {
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
        Node<T> current;
        int i;

        if (index <= (size / 2)) {
            current = head;
            i = 0;
            while (i != index) {
                current = current.next;
                i++;
            }
        } else {
            current = tail;
            i = size - 1;
            while (i != index) {
                current = current.previous;
                i--;
            }
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

    private void unlink(Node<T> current) {
        Node<T> next = current.next;
        Node<T> previous = current.previous;

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
    }

}
