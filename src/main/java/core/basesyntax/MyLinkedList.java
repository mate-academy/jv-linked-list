package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private int nodeIndex;

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

    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<T>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of list");
        }
        if (size == index) {
            add(value);
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> addedNode = new Node<>(currentNode.prev, value, currentNode);
            if (nodeIndex == 0) {
                head.prev = addedNode;
                addedNode.prev = null;
                addedNode.next = head;
                head = addedNode;
            } else {
                currentNode.prev.next = addedNode;
                currentNode.prev = addedNode;
                nodeIndex = 0;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            Node<T> newNode = new Node<>(tail, listElement, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        nodeIndex = 0;
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldNode = null;
        Node<T> currentNode = getNode(index);
        oldNode = currentNode.value;
        currentNode.value = value;
        nodeIndex = 0;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (!(currentNode.value == object
               || currentNode.value != null && currentNode.value.equals(object))) {
            currentNode = currentNode.next;
            nodeIndex++;
            if (nodeIndex == size) {
                nodeIndex = 0;
                return false;
            }
        }
        unlink(currentNode);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of list");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        while (nodeIndex != index) {
            currentNode = currentNode.next;
            nodeIndex++;
        }
        return currentNode;
    }

    private void unlink(Node<T> currentNode) {
        if (nodeIndex == 0 && size == 1) {
            currentNode.value = null;
        } else if (nodeIndex == 0 && size > 1) {
            currentNode.next.prev = null;
            head = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
            if (size - nodeIndex != 1) {
                currentNode.next.prev = currentNode.prev;
            }
        }
        nodeIndex = 0;
        size--;
    }
}
