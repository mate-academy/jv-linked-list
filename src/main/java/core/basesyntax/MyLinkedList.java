package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
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
        checkIndex(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value, null, head);
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        } else {
            Node<T> node = getNodeIndex(index);
            Node<T> newNode = new Node<>(value, node.prev,node);
            node.prev.next = newNode;
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexBound(index);
        Node<T> node = getNodeIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexBound(index);
        Node<T> currentNode = getNodeIndex(index);
        T lastValue = currentNode.value;
        currentNode.value = value;
        return lastValue;
    }

    @Override
    public T remove(int index) {
        checkIndexBound(index);
        return unlinkNode(getNodeIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null && currentNode.value == null
                    || object != null && object.equals(currentNode.value)) {
                unlinkNode(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds for size. Index "
                    + index + " size " + size);
        }
    }

    private void checkIndexBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " Please try again");
        }
    }

    private Node<T> getNodeIndex(int index) {
        Node<T> newNode;
        if (index <= size / 2) {
            newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        } else {
            newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
        }
        return newNode;
    }

    private T unlinkNode(Node<T> node) {
        final T value = node.value;
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        size--;
        return value;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
