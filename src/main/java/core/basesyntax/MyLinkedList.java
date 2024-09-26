package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
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
        checkIndex(index);
        Node<T> node = lookForNode(index);
        Node<T> prevNode = node.prev;
        Node<T> nextNode = new Node<>(prevNode, value, node);
        node.prev = nextNode;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = lookForNode(index);
        return node.element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = lookForNode(index);
        T tempValue = node.element;
        node.element = value;
        return tempValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = lookForNode(index);
        return unlinkNode(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.element
                    || object != null && object.equals(currentNode.element)) {
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is invalid.");
        }
    }

    private T unlinkNode(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.prev;
        if (node.prev == null) {
            head = node.next;
        } else {
            previousNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
            node.next = null;
        }
        size--;
        return node.element;
    }

    private Node<T> lookForNode(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }
}

