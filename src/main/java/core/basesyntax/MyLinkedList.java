package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(head, value, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size()) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> currentNode = getObjectByTheIndex(index);

            if (index == 0) {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            } else {
                newNode.next = currentNode;
                newNode.prev = currentNode.prev;
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;
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
        Node<T> currentNode = getObjectByTheIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getObjectByTheIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getObjectByTheIndex(index);
        T oldValue = currentNode.value;
        unlink(currentNode);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size(); i++) {
            if (currentNode.value == object
                    || (currentNode.value != null
                    && currentNode.value.equals(object))) {
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
        return size() == 0;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || (index >= size())) {
            throw new IndexOutOfBoundsException("Incorrect inputted data."
                            + "Please, check the index and the size.");
        }
    }

    private Node<T> getObjectByTheIndex(int index) {
        checkIndexForAdd(index);
        Node<T> currentNode;
        if (index < (size() / 2)) {
            currentNode = head;
            for (int currentIndex = 0; currentIndex < index; currentIndex++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int currentIndex = size() - 1; currentIndex > index; currentIndex--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = tail = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
