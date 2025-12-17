package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = addedNode;
        }
        if (tail != null) {
            tail.next = addedNode;
        }
        tail = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (index == size || (index == 0 && size == 0)) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev = currentNode.prev.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        T currentNodeItem = currentNode.item;
        currentNode.item = value;
        return currentNodeItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = findNodeByIndex(index);
        unlink(removed);
        size--;
        return removed.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((object == null && currentNode.item == null)
                    || (object != null && currentNode.item != null
                    && currentNode.item.equals(object))) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> nodeByIndex;
        nodeByIndex = index <= size / 2
                ? findNodeInFirstHalf(index)
                : findNodeInSecondHalf(index);
        return nodeByIndex;
    }

    private Node<T> findNodeInFirstHalf(int index) {
        Node<T> nodeByIndex = head;
        int counter = 0;
        while (counter != index) {
            nodeByIndex = nodeByIndex.next;
            counter++;
        }
        return nodeByIndex;
    }

    private Node<T> findNodeInSecondHalf(int index) {
        Node<T> nodeByIndex = tail;
        int counter = size - 1;
        while (counter != index) {
            nodeByIndex = nodeByIndex.prev;
            counter--;
        }
        return nodeByIndex;
    }

    private void unlink(Node<T> node) {
        if (size > 1) {
            if (node.equals(head)) {
                node.next.prev = node.prev;
                head = node.next;
            } else if (node.equals(tail)) {
                node.prev.next = node.next;
                tail = node.prev;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        } else {
            tail = head = null;
        }
        node.next = node.prev = null;
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
