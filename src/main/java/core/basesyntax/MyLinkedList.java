package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            size++;
        } else if (size == 1) {
            last = new Node<>(first, value, null);
            first.next = last;
            size++;
        } else {
            addNode(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            Node<T> currentNode = getNode(index);
            if (currentNode == first) {
                Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
                currentNode.prev = newNode;
                first = newNode;
            } else {
                Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addNode(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        return node == null ? null : node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node == null) {
            return false;
        }
        unlink(node);
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

    private Node<T> getNode(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> getNode(T value) {
        Node<T> currentNode = first;
        while (currentNode.next != null || currentNode.prev == null) {
            if ((currentNode.value == value)
                    || (currentNode != null && currentNode.value.equals(value))) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void addNode(T value) {
        Node<T> newNode = new Node<>(last.prev.next, value, null);
        last.prev.next.next = newNode;
        last = newNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " not in range for size " + size);
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        private Node() {

        }
    }
}
