package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
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
        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = getNodeByIndex(index);
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T replacedValue;
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        replacedValue = current.value;
        current.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        if (index == 0) {
            return unlinkFirst();
        }
        checkIndex(index);
        if (index == size - 1) {
            return unlinkLast();
        }
        return unlinkNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                if (i == 0) {
                    unlinkFirst();
                    return true;
                }
                if (i == size - 1) {
                    unlinkLast();
                    return true;
                }
                unlinkNode(currentNode);
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
        Node<T> newNode = new Node<>(value);
        newNode.next = head;
        newNode.next.previous = newNode;
        head = newNode;
        size++;
    }

    private void insertBefore(Node<T> currentNode, Node<T> newNode) {
        currentNode.previous.next = newNode;
        newNode.previous = currentNode.previous;
        currentNode.previous = newNode;
        newNode.next = currentNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
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

    private T unlinkNode(Node<T> node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;
        size--;
        return node.value;
    }

    private T unlinkFirst() {
        Node<T> unlinkedNode = head;
        if (size == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        size--;
        return unlinkedNode.value;
    }

    private T unlinkLast() {
        Node<T> unlinkedNode = tail;
        tail = unlinkedNode.previous;
        unlinkedNode.next = null;
        size--;
        return unlinkedNode.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.value = value;
        }
    }
}
