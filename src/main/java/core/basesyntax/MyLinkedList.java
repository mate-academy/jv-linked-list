package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node<T> newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> indexNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(indexNode.prev, value, indexNode);
            indexNode.prev.next = newNode;
            indexNode.prev = newNode;
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
        checkIndex(index, size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> replaceNode = getNodeByIndex(index);
        T deletedValue = replaceNode.value;
        replaceNode.value = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> deletedNode = getNodeByIndex(index);
        unLink(deletedNode);
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedNode;
        for (deletedNode = head; deletedNode != null; deletedNode = deletedNode.next) {
            if (object == deletedNode.value
                    || object != null && object.equals(deletedNode.value)) {
                unLink(deletedNode);
                return true;
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index > (size >> 1)) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void unLink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (size == 1) {
            tail = head = null;
        } else if (node.next == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else if (node.prev == null) {
            head.next.prev = null;
            head = head.next;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of list length " + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
