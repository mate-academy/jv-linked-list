package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> headNode;
    private Node<T> tailNode;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(tailNode, value, null);
        if (size == 0) {
            headNode = node;
        } else {
            tailNode.next = node;
        }
        tailNode = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeByIndex = getNode(index);
        Node<T> nodeBefore = nodeByIndex.prev;
        Node<T> newNode = new Node<>(nodeBefore, value, nodeByIndex);
        nodeByIndex.prev = newNode;
        if (nodeBefore == null) {
            headNode = newNode;
        } else {
            nodeBefore.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = getNode(index);
        T oldValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = getNode(index);
        T value = removedNode.value;
        unlink(removedNode);
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = headNode;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == null || currentNode.value.equals(object)) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds, please check input index");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = headNode;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        currentNode = tailNode;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        Node<T> nodeAfter = node.next;
        Node<T> nodeBefore = node.prev;
        if (nodeBefore == null) {
            headNode = nodeAfter;
        } else {
            nodeBefore.next = nodeAfter;
            node.prev = null;
        }
        if (nodeAfter == null) {
            tailNode = nodeBefore;
        } else {
            nodeAfter.prev = nodeBefore;
            node.next = null;
        }
        node.value = null;
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
