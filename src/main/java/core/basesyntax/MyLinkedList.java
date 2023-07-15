package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
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
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        Node<T> addedNode = new Node<>(currentNode.prev, value, currentNode);
        if (currentNode == head) {
            head.prev = addedNode;
            addedNode.next = head;
            head = addedNode;
        } else {
            currentNode.prev.next = addedNode;
            currentNode.prev = addedNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldNode = null;
        Node<T> currentNode = getNode(index);
        oldNode = currentNode.value;
        currentNode.value = value;
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
        int nodeIndex = 0;
        Node<T> currentNode = head;
        while (!(currentNode.value == object
               || currentNode.value != null && currentNode.value.equals(object))) {
            currentNode = currentNode.next;
            nodeIndex++;
            if (nodeIndex == size) {
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of list");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode;
        if (index > size / 2) {
            int nodeIndex = size - 1;
            currentNode = tail;
            while (nodeIndex != index) {
                currentNode = currentNode.prev;
                nodeIndex--;
            }
        } else {
            int nodeIndex = 0;
            currentNode = head;
            while (nodeIndex != index) {
                currentNode = currentNode.next;
                nodeIndex++;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode == head && size == 1) {
            currentNode.value = null;
        } else if (currentNode == head && size > 1) {
            currentNode.next.prev = null;
            head = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
            if (currentNode != tail) {
                currentNode.next.prev = currentNode.prev;
            }
        }
        size--;
    }
}
