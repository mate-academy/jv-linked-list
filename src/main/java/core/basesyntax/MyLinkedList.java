package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            newNode.prev = null;
            newNode.next = null;
            head = newNode;
            tail = newNode;
            incrementSize();
        } else {
            tail.next = newNode;
            newNode.next = null;
            newNode.prev = tail;
            tail = newNode;
            incrementSize();
        }
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            checkIndex(index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = head;
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            addInStart(newNode);
            return;
        } else {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            Node<T> prevNode = currentNode.prev;
            newNode.next = currentNode;
            newNode.prev = prevNode;
            prevNode.next = newNode;
            currentNode.prev = newNode;
            incrementSize();
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
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> currentNode = head;
        if (size == 1) {
            newNode.prev = null;
            newNode.next = null;
            head = newNode;
            tail = newNode;
            return currentNode.value;
        }
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T lastValue = currentNode.value;
        currentNode.value = value;
        return lastValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        if (index == 0) {
            removeFromStart();
            return currentNode.value;
        }
        if (index == size - 1) {
            removeFromEnd();
            return currentNode.value;
        }
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                if (i == 0) {
                    removeFromStart();
                    return true;
                }
                if (i == size - 1) {
                    removeFromEnd();
                    return true;
                }
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

    private void incrementSize() {
        size++;
    }

    private void decrementSize() {
        size--;
    }

    private void unlink(Node<T> node) {
        if (node.equals(head)) {
            node = node.next;
            node.prev = null;
            decrementSize();
            return;
        }
        if (node.equals(tail)) {
            node = node.prev;
            node.next = null;
            decrementSize();
            return;
        }
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        decrementSize();
    }

    private void addInStart(Node<T> node) {
        head.prev = node;
        node.next = head;
        head = node;
        incrementSize();
    }

    private void removeFromStart() {
        if (size == 1) {
            size--;
            return;
        }
        Node<T> currentNode = head.next;
        currentNode.prev = null;
        head = currentNode;
        decrementSize();
    }

    private void removeFromEnd() {
        if (size == 1) {
            size--;
            return;
        }
        Node<T> currentNode = tail.prev;
        currentNode.next = null;
        tail = currentNode;
        decrementSize();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index: " + index + " is out of bounds");
        }
    }
}
