package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
            size++;
            return;
        }
        newNode.previous = tail;
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addAsHead(value);
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node newNode = new Node(value);
        Node currentNode = getNodeByIndex(index);
        insertBefore(currentNode, newNode);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T replacedValue;
        checkIndex(index);
        Node current = getNodeByIndex(index);
        replacedValue = (T) current.value;
        current.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        return unlinkNode(index);
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || currentNode.value.equals(object) && currentNode.value != null) {
                unlinkNode(i);
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

    private void addAsHead(T value) {
        if (size == 0) {
            add(value);
            return;
        }
        Node newNode = new Node(value);
        newNode.next = head;
        newNode.next.previous = newNode;
        head = newNode;
        size++;
    }

    private void insertBefore(Node currentNode, Node newNode) {
        currentNode.previous.next = newNode;
        newNode.previous = currentNode.previous;
        currentNode.previous = newNode;
        newNode.next = currentNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getNodeByIndex(int index) {
        Node currentNode;
        if (size / 2 > index) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
            return currentNode;
        }
    }

    private T unlinkNode(int index) {
        Node unlinkedNode;
        if (size == 1 && index == 0) {
            unlinkedNode = head;
            head = tail = null;
            size--;
            return (T) unlinkedNode.value;
        }
        if (index == 0) {
            unlinkedNode = head;
            head = unlinkedNode.next;
            head.previous = null;
            size--;
            return (T) unlinkedNode.value;
        }
        checkIndex(index);
        if (index == size - 1) {
            unlinkedNode = tail;
            tail = unlinkedNode.previous;
            tail.next = null;
            size--;
            return (T) unlinkedNode.value;
        }
        unlinkedNode = getNodeByIndex(index);
        unlinkedNode.previous.next = unlinkedNode.next;
        unlinkedNode.next.previous = unlinkedNode.previous;
        size--;
        return (T) unlinkedNode.value;
    }

    private static class Node<T> {
        private T value;
        private Node next;
        private Node previous;

        public Node(T value) {
            this.value = value;
        }
    }
}
