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
        if (size() == index) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            Node<T> currNode = getObjectByTheIndex(index);

            if (index == 0) {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            } else {
                newNode.next = currNode;
                newNode.prev = currNode.prev;
                newNode.prev.next = newNode;
                currNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> currNode = getObjectByTheIndex(index);
        return currNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currNode = getObjectByTheIndex(index);
        T oldValue = currNode.value;
        currNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currNode = getObjectByTheIndex(index);
        T oldValue = currNode.value;
        unlink(currNode);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currNode = head;
        for (int i = 0; i < size(); i++) {
            if (currNode.value == object
                    || (currNode.value != null
                    && currNode.value.equals(object))) {
                unlink(currNode);
                size--;
                return true;
            }
            currNode = currNode.next;
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

    private Node<T> getObjectByTheIndex(int index) {
        checkIndexForAdd(index);
        Node<T> currNode;
        if (index < (0.5 * size())) {
            currNode = head;
            for (int currIndex = 0; currIndex < index; currIndex++) {
                currNode = currNode.next;
            }
        } else {
            currNode = tail;
            for (int currIndex = size() - 1; currIndex > index; currIndex--) {
                currNode = currNode.prev;
            }
        }
        return currNode;
    }

    private void checkIndexForAdd(int index) {
        if (size() <= index || index > 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
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
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
