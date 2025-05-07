package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        if (index == 0) {
            if (size == 0) {
                add(value);
                return;
            }
            Node<T> rightNeighbor = head;
            Node<T> node = new Node<>(null, value, rightNeighbor);
            head = node;
            rightNeighbor.prev = node;
            size++;
        } else {
            Node<T> tempNode = head;
            for (int i = 0; i < index - 1; i++) {
                tempNode = tempNode.next;
            }
            Node<T> leftNeighbor = tempNode;
            Node<T> rightNeighbor = tempNode.next;
            Node<T> node = new Node<>(leftNeighbor, value, rightNeighbor);
            leftNeighbor.next = node;
            if (rightNeighbor != null) {
                rightNeighbor.prev = node;
            } else {
                tail = node;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> tempNode = head;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        return tempNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeToReplace = head;
        for (int i = 0; i < index; i++) {
            nodeToReplace = nodeToReplace.next;
        }
        T returnValue = nodeToReplace.item;
        nodeToReplace.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToDelete = head;
        if (index == 0) {
            head = nodeToDelete.next;
        }
        for (int i = 0; i < index; i++) {
            nodeToDelete = nodeToDelete.next;
        }
        if (nodeToDelete.prev != null) {
            nodeToDelete.prev.next = nodeToDelete.next;
        }
        if (nodeToDelete.next != null) {
            nodeToDelete.next.prev = nodeToDelete.prev;
        }
        size--;
        return nodeToDelete.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToDelete = head;
        for (int i = 0; i < size; i++) {
            if (nodeToDelete.item == null && object == null
                    || object != null && object.equals(nodeToDelete.item)) {
                if (i == 0) {
                    head = nodeToDelete.next;
                }
                if (nodeToDelete.prev != null) {
                    nodeToDelete.prev.next = nodeToDelete.next;
                }
                if (nodeToDelete.next != null) {
                    nodeToDelete.next.prev = nodeToDelete.prev;
                }
                size--;
                return true;
            }
            nodeToDelete = nodeToDelete.next;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
