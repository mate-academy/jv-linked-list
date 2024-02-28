package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final double HALF = 0.5;
    private static final int ONE = 1;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndexNotInclusive(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = head;

        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            size++;
            return;
        }

        currentNode = findNodeByIndex(index - ONE);
        Node<T> nextNode = currentNode.next;
        newNode.next = nextNode;
        if (nextNode != null) {
            nextNode.prev = newNode;
        }
        currentNode.next = newNode;
        newNode.prev = currentNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        validateIndexInclusive(index);
        Node<T> current = head;
        current = findNodeByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndexInclusive(index);
        Node<T> current = head;
        current = findNodeByIndex(index);
        T result = current.value;
        current.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        validateIndexInclusive(index);
        Node<T> current = head;
        if (index == 0) {
            head = current.next;
            current.prev = null;
            size--;
            return current.value;
        }
        current = findNodeByIndex(index);
        Node<T> nextNode = current.next;
        Node<T> prevNode = current.prev;

        if (nextNode != null) {
            nextNode.prev = prevNode;
        }
        if (prevNode != null) {
            prevNode.next = nextNode;
        }

        size--;
        return current.value;

    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        for (int i = 0; i < size; i++) {
            if ((current.value == null && object == null)
                    || (current.value != null && object != null
                    && current.value.equals(object))) {
                remove(i);
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

    private void validateIndexInclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Bad index " + index);
        }
    }

    private void validateIndexNotInclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Bad index " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (size - index > size * HALF) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = 0; i < size - index - ONE; i++) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }
}
