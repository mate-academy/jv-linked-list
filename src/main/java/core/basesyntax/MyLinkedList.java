package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
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
        Node<T> nextNode = findNodeByIndex(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        nextNode.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldVal = node.item;
        node.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = findNodeByIndex(index);
        T removedValue = nodeToRemove.item;
        unlinkNode(nodeToRemove);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if (object == null ? currentNode.item == null : object.equals(currentNode.item)) {
                unlinkNode(currentNode);
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
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> resultNode;
        if (index < (size >> 1)) {
            resultNode = head;
            for (int i = 0; i < index; i++) {
                resultNode = resultNode.next;
            }
        } else {
            resultNode = tail;
            for (int i = size - 1; i > index; i--) {
                resultNode = resultNode.prev;
            }
        }
        return resultNode;
    }

    private void unlinkNode(Node<T> nodeToUnlink) {
        final Node<T> next = nodeToUnlink.next;
        final Node<T> prev = nodeToUnlink.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            nodeToUnlink.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            nodeToUnlink.next = null;
        }

        nodeToUnlink.item = null;
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
