package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexExclusive(index);
        Node<T> newNode = new Node<>(value, null, null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> currentNode = getNode(index);
            insertBefore(currentNode, newNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToUpdate = getNode(index);
        T oldValue = nodeToUpdate.data;
        nodeToUpdate.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        return unlink(nodeToRemove).data;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexOfNode(object);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void validateIndexExclusive(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index provided");
        }
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index provided");
        }
    }

    private Node<T> getNode(int index) {
        validateIndex(index);
        if (index == 0) {
            return head;
        } else if (index == size - 1) {
            return tail;
        }
        Node<T> currentNode;
        int steps = stepsToTake(index);
        currentNode = steps > 0 ? head : tail;

        // Should I reverse if and for order ?
        // It will add to readability, but it will execute a bit longer
        if (steps > 0) {
            for (int i = 0; i < steps; i++) {
                currentNode = currentNode.next;
            }
        } else {
            // Should change steps sign to plus
            for (int i = 0; i < -steps; i++) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private boolean equals(T a, T b) {
        return (a == b) || (a != null && a.equals(b));
    }

    private int getIndexOfNode(T value) {
        for (int i = 0; i < size; i++) {
            if (equals(value, getNode(i).data)) {
                return i;
            }
        }
        return -1;
    }

    // Using true to start from head, false to start from tail
    private int sideOfListToStart(int index) {
        if (index <= size / 2) {
            return index;
        } else {
            return -(size - 1 - index);
        }
    }

    private void insertBefore(Node<T> nodeToInsertAfter, Node<T> newNode) {
        newNode.prev = nodeToInsertAfter.prev;
        newNode.next = nodeToInsertAfter;
        nodeToInsertAfter.prev.next = newNode;
        nodeToInsertAfter.prev = newNode;
    }

    private void printLinkedList() {
        Node<T> currentNode = head;

        while (currentNode != null) {
            System.out.println(currentNode.data);
            currentNode = currentNode.next;
        }
    }

    private void printReversedLinkedList() {
        Node<T> currentNode = tail;

        while (currentNode != null) {
            System.out.println(currentNode.data);
            currentNode = currentNode.prev;
        }
    }

    private Node<T> unlink(Node<T> node) {
        size--;
        if (node == tail) {
            tail = node.prev;
            return node;
        }
        if (node == head) {
            head = node.next;
            return node;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return node;
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        private Node(E data, Node<E> next, Node<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
