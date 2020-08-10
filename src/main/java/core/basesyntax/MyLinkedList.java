package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    @Override
    public boolean add(T value) {
        addNodeToTheEnd(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index is out of bounds.");
        }
        if (index == size) {
            addNodeToTheEnd(value);
        } else {
            Node<T> soughtForNode = findNodeByIndex(index);
            Node<T> previousNode = soughtForNode.prev;
            Node<T> newNode = new Node<>(value, soughtForNode, previousNode);
            soughtForNode.prev = newNode;
            if (previousNode == null) {
                firstNode = newNode;
            } else {
                previousNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list.size() == 0) {
            return false;
        }
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> soughtForNode = findNodeByIndex(index);
        return soughtForNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeToBeShifted = findNodeByIndex(index);
        T previousValue = nodeToBeShifted.value;
        nodeToBeShifted.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToBeRemoved = findNodeByIndex(index);
        removeNodeLinks(nodeToBeRemoved);
        return nodeToBeRemoved.value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> nodeToBeRemoved = firstNode;
        for (int i = 0; i < size; i++) {
            if (nodeToBeRemoved.value == t
                    || nodeToBeRemoved.value != null && nodeToBeRemoved.value.equals(t)) {
                removeNodeLinks(nodeToBeRemoved);
                return true;
            }
            nodeToBeRemoved = nodeToBeRemoved.next;
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

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> soughtForNode;
        if (index < size >> 1) {
            soughtForNode = firstNode;
            for (int i = 0; i < index; i++) {
                soughtForNode = soughtForNode.next;
            }
        } else {
            soughtForNode = lastNode;
            for (int i = size - 1; i > index; i--) {
                soughtForNode = soughtForNode.prev;
            }
        }
        return soughtForNode;
    }

    private void removeNodeLinks(Node<T> nodeToBeRemoved) {
        Node<T> previousNode = nodeToBeRemoved.prev;
        Node<T> nextNode = nodeToBeRemoved.next;
        if (previousNode == null) {
            firstNode = nextNode;
        } else {
            previousNode.next = nextNode;
        }
        if (nextNode == null) {
            lastNode = previousNode;
        } else {
            nextNode.prev = previousNode;
        }
        size--;
    }

    private void addNodeToTheEnd(T value) {
        Node<T> currentLastNode = lastNode;
        lastNode = new Node<>(value, null, currentLastNode);
        if (currentLastNode == null) {
            firstNode = lastNode;
        } else {
            currentLastNode.next = lastNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is out of bounds.");
        }
    }
}
