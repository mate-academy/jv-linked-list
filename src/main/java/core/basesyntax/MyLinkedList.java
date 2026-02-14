package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int IS_EMPTY = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == IS_EMPTY) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (size == IS_EMPTY) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
        } else if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> node = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = node;
            currentNode.prev = node;
        }
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        indexValidation(index);
        Node<T> currentNode = findNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        Node<T> currentNode = findNodeByIndex(index);
        final T removedValue = currentNode.value;
        unlink(currentNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                break;
            }
            currentNode = currentNode.next;
        }
        if (currentNode == null) {
            return false;
        } else {
            unlink(currentNode);
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void indexValidation(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
        node.value = null;
        node.next = null;
        node.prev = null;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = head;
        if (index < (size / 2)) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = (size - 1); i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

    }
}
