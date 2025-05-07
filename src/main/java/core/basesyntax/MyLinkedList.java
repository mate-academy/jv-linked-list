package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> nodeToAdd = new Node<>(null, value, null);
        if (size == 0) {
            tail = nodeToAdd;
            head = tail;
        } else {
            nodeToAdd.prev = tail;
            tail.next = nodeToAdd;
            tail = nodeToAdd;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> nodeToAddByIndex = new Node<>(null, value, null);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            nodeToAddByIndex.next = head;
            head = nodeToAddByIndex;
            head.next.prev = head;
            size++;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            nodeToAddByIndex.prev = currentNode.prev;
            currentNode.prev.next = nodeToAddByIndex;
            nodeToAddByIndex.next = currentNode;
            currentNode.prev = nodeToAddByIndex;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    || currentNode.value != null
                    && currentNode.value.equals(object)) {
                unlink(currentNode);
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

    private Node<T> getNodeByIndex(int index) {
        verifyIndex(index);
        Node<T> nodeAtIndex = null;
        if (index < size / 2) {
            nodeAtIndex = head;
            for (int i = 0; i < index; i++) {
                nodeAtIndex = nodeAtIndex.next;
            }
            return nodeAtIndex;
        } else {
            nodeAtIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeAtIndex = nodeAtIndex.prev;
            }
            return nodeAtIndex;
        }
    }

    private T unlink(Node<T> nodeToUnlink) {
        Node<T> previousNode = nodeToUnlink.prev;
        Node<T> nextNode = nodeToUnlink.next;
        if (previousNode == null) {
            head = nextNode;
        } else if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
            previousNode.next = nextNode;
        }
        size--;
        return nodeToUnlink.value;
    }

    private void verifyIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
