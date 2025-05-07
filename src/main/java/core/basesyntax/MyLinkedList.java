package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index when add node");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T elementOfList : list) {
            linkLast(elementOfList);
            size++;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> foundNode = getNodeByIndex(index);
        return foundNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeToSet = getNodeByIndex(index);
        T oldValue = nodeToSet.value;
        nodeToSet.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        T value = nodeToRemove.value;
        unlinkNode(nodeToRemove);
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
                unlinkNode(node);
                size--;
                return true;
            }
            node = node.nextNode;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> nodeByIndex;
        if (index < size >> 1) {
            nodeByIndex = head;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.nextNode;
            }
        } else {
            nodeByIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prevNode;
            }
        }
        return nodeByIndex;
    }

    private void unlinkNode(Node<T> currentNode) {
        Node<T> prev = currentNode.prevNode;
        Node<T> next = currentNode.nextNode;
        if (prev == null && next == null) {
            head = null;
            tail = null;
            return;
        }
        if (prev == null) {
            head = next;
            next.prevNode = null;
            return;
        }
        if (next == null) {
            tail = prev;
            prev.nextNode = null;
            return;
        }
        prev.nextNode = currentNode.nextNode;
        next.prevNode = currentNode.prevNode;
    }

    private void linkLast(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.nextNode = newNode;
        }
    }

    private void linkBefore(T value, Node<T> indexNode) {
        Node<T> currentIndexNode = indexNode.prevNode;
        Node<T> currentNewNode = new Node<>(currentIndexNode, value, indexNode);
        indexNode.prevNode = currentNewNode;
        if (currentIndexNode == null) {
            head = currentNewNode;
        } else {
            currentIndexNode.nextNode = currentNewNode;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prevNode;
        private Node<T> nextNode;

        public Node(Node<T> prevNode, T value, Node<T> nextNode) {
            this.prevNode = prevNode;
            this.value = value;
            this.nextNode = nextNode;
        }
    }
}
