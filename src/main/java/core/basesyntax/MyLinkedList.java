package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, tail);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value, head, null);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> requiredNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(value, requiredNode, requiredNode.prev);
        requiredNode.prev.next = newNode;
        requiredNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listIn : list) {
            add(listIn);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> requiredNode = getNodeByIndex(index);
        T oldValue = requiredNode.value;
        requiredNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removableNode = getNodeByIndex(index);
        disconnect(removableNode);
        return removableNode.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                disconnect(currentNode);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<T> requiredNode = head;
            for (int i = 0; i < index; i++) {
                requiredNode = requiredNode.next;
            }
            return requiredNode;
        } else {
            Node<T> requiredNode = tail;
            for (int i = size - 1; i > index; i--) {
                requiredNode = requiredNode.prev;
            }
            return requiredNode;
        }
    }

    private void disconnect(Node<T> removableNode) {
        final Node<T> next = removableNode.next;
        final Node<T> prev = removableNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            removableNode.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            removableNode.next = null;
        }
        size--;
    }

}
