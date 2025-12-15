package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            if (size > 0) {
                Node<T> currentNode = findNodeByIndex(index);
                newNode.next = currentNode;
                currentNode.prev = newNode;
            }
            head = newNode;
        }
        if (size == index) {
            if (size > 0) {
                Node<T> prevNode = findNodeByIndex(index - 1);
                newNode.prev = prevNode;
                prevNode.next = newNode;
            }
            tail = newNode;
        }
        if (index > 0 && index < size) {
            Node<T> currentNode = findNodeByIndex(index);
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev = currentNode.prev.next = newNode;
        }
        size++;
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
        if (!head.equals(tail)) {
            removeNode(removed);
        }
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
                if (!head.equals(tail)) {
                    removeNode(currentNode);
                }
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
        Node<T> nodeByIndex = head;
        int counter = 0;
        while (counter != index) {
            nodeByIndex = nodeByIndex.next;
            counter++;
        }
        return nodeByIndex;
    }

    private void removeNode(Node<T> removed) {
        if (removed.equals(head)) {
            removed.next.prev = removed.prev;
            head = removed.next;
        } else if (removed.equals(tail)) {
            removed.prev.next = removed.next;
            tail = removed.prev;
        } else {
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        }
    }

    private void unlink(Node<T> node) {
        node.next = node.prev = null;
    }
}
