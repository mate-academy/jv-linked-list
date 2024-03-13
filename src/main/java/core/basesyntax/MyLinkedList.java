package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, "add");
        if (index == size()) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> currentNode = searchNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, "get");
        Node<T> getNode = searchNode(index);
        return getNode.value;
    }

    @Override
    public T set(T value, int index) {
        T removeValue;
        checkIndex(index, "set");
        Node<T> currentNode = searchNode(index);
        removeValue = currentNode.value;
        currentNode.value = value;
        return removeValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, "remove");
        Node<T> currentNode = searchNode(index);
        unlink(currentNode);
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size();i++) {
            if (object == currentNode.value
                    || object != null
                    && object.equals(currentNode.value)) {
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
        return head == null;
    }

    private void unlink(Node<T> node) {
        if (node.equals(tail) && node.equals(head)) {
            head = tail = null;
        } else if (node.equals(head)) {
            node.next.prev = null;
            head = node.next;
        } else if (node.equals(tail)) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private Node searchNode(int index) {
        Node<T> currentNode;
        if (size() - index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; index < i; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index, String action) {
        if ((index < 0 || index > size) && action.equals("add")) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " doesn't exist. Size is equal " + size);
        } else if ((index < 0
                || index >= size)
                && (action.equals("remove")
                || action.equals("get")
                || action.equals("set"))) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " doesn't exist. Size is equal " + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
