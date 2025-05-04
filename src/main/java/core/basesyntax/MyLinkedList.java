package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAddMethod(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
        }
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
        checkIndexForAddMethod(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForAddMethod(index);
        T oldValue = findNodeByIndex(index).item;
        findNodeByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        T removedElement = findNodeByIndex(index).item;
        unlink(findNodeByIndex(index));
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (areEquals(object, currentNode.item)) {
                unlink(currentNode);
                size--;
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

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> newNode = first;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        return newNode;
    }

    private void checkIndexForAddMethod(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        } else {
            first = currentNode.next;
        }

        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        } else {
            last = currentNode.prev;
        }
    }

    private void linkBefore(T node, Node<T> nodeNext) {
        if (nodeNext == null) {
            throw new RuntimeException("Successor node cant not be null");
        }

        Node<T> nodePrevious = nodeNext.prev;
        Node<T> newNode = new Node<>(nodePrevious, node, nodeNext);
        nodeNext.prev = newNode;
        if (nodePrevious == null) {
            first = newNode;
        } else {
            nodePrevious.next = newNode;
            newNode.prev = nodePrevious;
        }
    }

    private void linkLast(T node) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(last, node, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
    }

    private boolean areEquals(T nodeOne, T nodeTwo) {
        if (nodeOne == null) {
            return nodeTwo == null;
        }
        return nodeOne.equals(nodeTwo);
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev,T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
