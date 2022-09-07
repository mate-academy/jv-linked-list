package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    @Override
    public void add(T value) {
        final Node<T> newNode;

        if (tail == null) {
            newNode = new Node<>(null, value, null);
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
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

        final Node<T> nodeOnIndex = getNode(index);
        final Node<T> newNode = new Node<>(nodeOnIndex.prev, value, nodeOnIndex);

        if (index == 0) {
            head = newNode;
        } else {
            nodeOnIndex.prev.next = newNode;
        }

        nodeOnIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        final Node<T> nodeOnIndex = getNode(index);
        final T oldValue = nodeOnIndex.item;
        nodeOnIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        final Node<T> nodeOnIndex = getNode(index);
        unlinkNode(nodeOnIndex);
        size--;
        return nodeOnIndex.item;
    }

    @Override
    public boolean remove(T value) {
        final Node<T> nodeToRemove = getNode(value);
        if (nodeToRemove == null) {
            return false;
        }

        unlinkNode(nodeToRemove);
        size--;
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

    private void unlinkNode(Node<T> node) {
        if (node == null) {
            return;
        }

        if (node != head && node != tail) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return;
        }

        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        }

        if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            }
        }
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                                                + " is out of bounds for size " + size);
        }

        if (index < size / 2) {
            int currIndex = 0;
            Node<T> currNode = head;
            while (currIndex != index) {
                currNode = currNode.next;
                currIndex++;
            }
            return currNode;
        }

        Node<T> currNode = tail;
        int currIndex = size - 1;
        while (currIndex != index) {
            currNode = currNode.prev;
            currIndex--;
        }
        return currNode;
    }

    private Node<T> getNode(T value) {
        Node<T> currNode = head;
        while (currNode != null) {
            if (value == null && currNode.item == null
                    || value != null && value.equals(currNode.item)) {
                return currNode;
            }
            currNode = currNode.next;
        }
        return null;
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
