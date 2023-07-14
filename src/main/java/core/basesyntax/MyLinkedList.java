package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        ++size;
    }

    @Override
    public void add(T value, int index) {
        indexRangeValid(index);
        if (index == 0 && size > 0) {
            Node<T> oldHeadNode = head;
            head = new Node<>(null, value, oldHeadNode);
            oldHeadNode.prev = head;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> nodeOfIndex = getNodeOfIndex(index);
            Node<T> newNode = new Node<>(nodeOfIndex.prev, value, nodeOfIndex);
            nodeOfIndex.prev.next = newNode;
            nodeOfIndex.prev = newNode;
        }
        ++size;
    }

    @Override
    public void addAll(List<T> list) {
        for (T elem : list) {
            add(elem);
        }
    }

    @Override
    public T get(int index) {
        return getNodeOfIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeOfIndex = getNodeOfIndex(index);
        T oldValue = nodeOfIndex.item;
        nodeOfIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeOfIndex(index);
        if (nodeToRemove != null) {
            removeNode(nodeToRemove);
            return nodeToRemove.item;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }

        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.item || object != null && object.equals(currentNode.item)) {
                removeNode(currentNode);
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

    private void indexRangeValid(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid Index!");
        }
    }

    private Node<T> getNodeOfIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }

        int middleList = size / 2;
        Node<T> iterator;
        if (middleList >= index) {
            iterator = head;
            for (int i = 0; i < index; ++i) {
                iterator = iterator.next;
            }
        } else {
            iterator = tail;
            for (int i = size - 1; i > index; --i) {
                iterator = iterator.prev;
            }
        }
        return iterator;
    }

    private void removeNode(Node<T> node) {
        if (node == null) {
            return;
        }

        if (node == head) {
            head = node.next;
        } else {
            Node<T> prevNode = node.prev;
            prevNode.next = node.next;
            if (node.next != null) {
                node.next.prev = prevNode;
            } else {
                tail = prevNode;
            }
        }
        --size;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
