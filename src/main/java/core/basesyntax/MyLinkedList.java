package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node(null, value, null);
            tail = head;
        } else {
            Node secondLast = tail;
            tail = new Node(secondLast, value, null);
            secondLast.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node nodeAtIndex = getNode(index);
        Node newNode = new Node(nodeAtIndex.previous, value, nodeAtIndex);

        if (nodeAtIndex.previous != null) {
            nodeAtIndex.previous.next = newNode;
        } else {
            head = newNode;
        }
        nodeAtIndex.previous = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node node = head;
        while (node != null) {
            if (Objects.equals(node.value, object)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + " " + size);
        }

        Node node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private void unlink(Node node) {
        Node nodePrevious = node.previous;
        Node nodeNext = node.next;

        if (nodePrevious != null) {
            nodePrevious.next = nodeNext;
        } else {
            head = nodeNext;
        }

        if (nodeNext != null) {
            nodeNext.previous = nodePrevious;
        } else {
            tail = nodePrevious;
        }
        size--;
    }

    private class Node {
        private Node previous;
        private T value;
        private Node next;

        public Node(Node previous, T value, Node next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
