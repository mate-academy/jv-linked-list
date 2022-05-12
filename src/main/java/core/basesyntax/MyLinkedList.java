package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
        public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
        public void add(T value, int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node currentNode = new Node<>(null, value, head);
            head = currentNode;
            size++;
        } else {
            Node currentNode = findNode(index);
            Node prevNode = currentNode.prev;
            Node newNode = new Node(prevNode, value, currentNode);
            currentNode.prev = newNode;
            prevNode.next = newNode;
            if (currentNode == tail) {
                newNode = tail;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i), size);
        }
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        Node currentNode = findNode(index);
        T oldValue = (T)currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        Node currentNode = findNode(index);
        T oldValue = (T)currentNode.value;
        unlinkNode(currentNode);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node node = head; node != null; node = node.next) {
            if (node.value == null || node.value.equals(object)) {
                unlinkNode(node);
                size--;
                return true;
            }
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

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode = head;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unlinkNode(Node<T> node) {
        Node beforeNode;
        Node afterNode;
        if (node.prev == null) {
            afterNode = node.next;
            head = afterNode;
        } else if (node.next == null) {
            beforeNode = node.prev;
            tail = beforeNode;
        } else {
            afterNode = node.next;
            beforeNode = node.prev;
            afterNode.prev = beforeNode;
            beforeNode.next = afterNode;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        }
    }
}
