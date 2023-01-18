package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null || size() == 0) {
            head = new Node<>(null, null, null);
            tail = new Node<>(null, null, null);
            head.next = tail;
            tail.prev = head;
        }
        if (head.value == null) {
            head.value = value;
            return;
        }
        if (size() == 1) {
            tail.value = value;
            return;
        }
        Node<T> addNewNode = new Node<>(null, value, null);
        Node<T> currentNode = tail;
        tail = addNewNode;
        tail.prev = currentNode;
        currentNode.next = tail;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("invalid index");
        }
        if (index == size()) {
            add(value);
            return;
        }
        Node<T> target = getNode(index);
        linkBefore(value, target);
    }

    private void linkBefore(T value, Node<T> newNodeBefore) {
        Node<T> newNode = new Node<>(null, value, null);
        if (newNodeBefore == head) {
            newNode.next = newNodeBefore;
            newNodeBefore.prev = newNode;
            head = newNode;
            return;
        }
        Node<T> previousNode = newNodeBefore.prev;
        newNode.next = newNodeBefore;
        newNodeBefore.prev = newNode;
        previousNode.next = newNode;
        newNode.prev = previousNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        exceptionForIndex(index);
        Node<T> currentNode = getNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        exceptionForIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        exceptionForIndex(index);
        Node<T> currentNode;
        currentNode = getNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(object, currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        if (size() == 1) {
            head = new Node<>(null, null, null);
            return;
        }
        if (node == head) {
            head = head.next;
            node.next.prev = null;
            return;
        }
        if (node == tail) {
            node.prev.next = null;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            int middleSize = size() / 2;
            if (middleSize < index) {
                currentNode = tail;
                for (int j = size() - 1; j > index; j--) {
                    currentNode = currentNode.prev;
                }
            } else {
                currentNode = head;
                for (int j = 0; j < index; j++) {
                    currentNode = currentNode.next;
                }
            }
        }
        return currentNode;
    }

    private void exceptionForIndex(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException("index: "
                    + index
                    + " is bigger than or equals size: "
                    + size());
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index:" + index + " is less than 0");
        }
    }

    @Override
    public int size() {
        Node<T> findNode = head;
        if (findNode == null || (findNode.value == null && findNode.next == null)) {
            return 0;
        }

        if (findNode.next == null || findNode.value != null
                && findNode.next.value == null && findNode.next.next == null) {
            return 1;
        }
        int counter = 0;
        while (findNode != null) {
            findNode = findNode.next;
            counter++;
        }
        return counter;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private static class Node<T> {
        private Node<T> next;
        private T value;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
