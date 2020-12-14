package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private boolean isInvalidIndex(int index) {
        return index < 0 || index >= size;
    }

    @Override
    public boolean add(T value) {
        linkToTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkToTail(value);
        } else if (!isInvalidIndex(index)) {
            linkToNode(value, getNodeByIndex(index));
        }
        if (isInvalidIndex(index)) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
        return true;
    }

    private void linkToNode(T elementToInsert, Node<T> successor) {
        Node<T> predecessor = successor.prev;
        Node<T> newNode = new Node<>(predecessor, elementToInsert, successor);
        successor.prev = newNode;
        if (predecessor == null) {
            head = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    private void linkToTail(T elementToInsert) {
        Node<T> predecessor = tail;
        Node<T> newNode = new Node<>(predecessor, elementToInsert, null);
        tail = newNode;
        if (predecessor == null) {
            head = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    @Override
    public T get(int index) {
        if (isInvalidIndex(index)) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
        return getNodeByIndex(index).value;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> targetNode;
        if (index > size / 2) {
            targetNode = tail;
            for (int i = size - 1; i > index; i--) {
                targetNode = targetNode.prev;
            }
        } else {
            targetNode = head;
            for (int i = 0; i < index; i++) {
                targetNode = targetNode.next;
            }
        }
        return targetNode;
    }

    @Override
    public T set(T value, int index) {
        if (isInvalidIndex(index)) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
        Node<T> oldElement = getNodeByIndex(index);
        T oldNodeValue = oldElement.value;
        oldElement.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        if (isInvalidIndex(index)) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
        return removeNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((object == null && currentNode.value == null)
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                removeNode(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private T removeNode(Node<T> nodeToRemove) {
        if (nodeToRemove.next == null) {
            tail = nodeToRemove.prev;
            nodeToRemove.prev = null;
        } else if (nodeToRemove.prev == null) {
            head = nodeToRemove.next;
            nodeToRemove.next = null;
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
            nodeToRemove.prev = null;
            nodeToRemove.next = null;
        }
        size--;
        return nodeToRemove.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
