package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(value, null, null);
            tail = head;
            size++;
        } else {
            Node<T> newNode = new Node<>(value, tail, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node selectedNode = getNodeByIndex(index);
        if (selectedNode == head) {
            Node newNode = new Node<>(value, null, selectedNode);
            selectedNode.prev = newNode;
            head = newNode;
        } else {
            Node newNode = new Node<>(value, selectedNode.prev, selectedNode);
            selectedNode.prev.next = newNode;
            selectedNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return (T) getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node currentNode = getNodeByIndex(index);
        T oldValue = (T) currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node nodeToDelete = getNodeByIndex(index);
        unlink(nodeToDelete);
        size--;
        return (T) nodeToDelete.item;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((currentNode.item == object)
                    || (currentNode.item != null && currentNode.item.equals(object))) {
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

    private void unlink(Node nodeToDelete) {
        if (head == nodeToDelete) {
            if (head != tail) {
                head = nodeToDelete.next;
                nodeToDelete.next.prev = null;
            }
            nodeToDelete.next = null;
        } else if (tail == nodeToDelete) {
            tail = nodeToDelete.prev;
            nodeToDelete.prev.next = null;
            nodeToDelete.prev = null;
        } else {
            nodeToDelete.prev.next = nodeToDelete.next;
            nodeToDelete.next.prev = nodeToDelete.prev;
            nodeToDelete.prev = null;
            nodeToDelete.next = null;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (size / 2 < index) {
            Node<T> currentNode = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (index == i) {
                    return currentNode;
                }
                currentNode = currentNode.prev;
            }
        } else {
            Node<T> currentNode = head;
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    return currentNode;
                }
                currentNode = currentNode.next;
            }
        }
        throw new IndexOutOfBoundsException("Can't find element with index " + index);
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
