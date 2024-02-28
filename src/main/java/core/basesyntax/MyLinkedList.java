package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final double HALF = 0.5;
    private static final String INVALID_INDEX_MESSAGE = "Bad index: %d for size: %d";
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
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
        validateIndexOnAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = new Node<>(value, null, null);
        if (linkToHead(index, current)) {
            return;
        }
        linkByIndex(index, current);
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        Node<T> current = findNodeByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> current = findNodeByIndex(index);
        T result = current.value;
        current.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> current = findNodeByIndex(index);
        if (index == 0) {
            head = current.next;
            current.prev = null;
            size--;
            return current.value;
        }
        unlink(current);
        return current.value;

    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        for (int i = 0; i < size; i++) {
            if ((current.value == object)
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format(INVALID_INDEX_MESSAGE, index, size));
        }
    }

    private void validateIndexOnAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format(INVALID_INDEX_MESSAGE, index, size));
        }
    }

    private void linkByIndex(int index, Node<T> newNode) {
        Node<T> currentNode = findNodeByIndex(index - 1);
        Node<T> nextNode = currentNode.next;
        newNode.next = nextNode;
        if (nextNode != null) {
            nextNode.prev = newNode;
        }
        currentNode.next = newNode;
        newNode.prev = currentNode;
        size++;
    }

    private boolean linkToHead(int index, Node<T> newNode) {
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            size++;
            return true;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (nextNode != null) {
            nextNode.prev = prevNode;
        }
        if (prevNode != null) {
            prevNode.next = nextNode;
        }
        size--;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (size - index > size * HALF) {
            currentNode = findFromHead(index);
        } else {
            currentNode = findFromTail(index);
        }
        return currentNode;
    }

    private Node<T> findFromTail(int index) {
        Node<T> currentNode;
        currentNode = tail;
        for (int i = 0; i < size - index - 1; i++) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private Node<T> findFromHead(int index) {
        Node<T> currentNode;
        currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        private Node(T value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
