package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            Node<T> newNode = new Node<T>(null, value, null);
            head = newNode;
            tail = head;
        } else {
            Node<T> newNode = new Node<T>(tail, value, null);
            newNode.prev.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (checkIndex(index)) {
            Node<T> currentNode = searchElement(index);
            Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);
            newNode.prev.next = newNode;
            currentNode.prev = newNode;
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
        Node<T> currentNode = null;
        if (checkIndex(index)) {
            currentNode = searchElement(index);
        }
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> sameNode = null;
        if (checkIndex(index)) {
            sameNode = searchElement(index);
            sameNode.item = value;
        }
        return sameNode.item;
    }

    @Override
    public T remove(int index) {
        Node<T> delNode = null;
        if (checkIndex(index)) {
            delNode = searchElement(index);
            unlink((T) delNode);
        }
        return delNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = null;
        currentNode = searchNode(object);
        currentNode.prev = currentNode.next;
        currentNode.next = currentNode.prev;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public void unlink(T object) {
        Node<T> currentNode = null;
        currentNode = (Node<T>) object;
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
    }

    public Node<T> searchNode(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.equals(object)) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public Node<T> searchElement(int index) {
        int count = 0;
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (index == count) {
                break;
            }
            count++;
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public Node<T> lastNode() {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.next == null) {
                break;
            }
        }
        return currentNode;
    }

    public boolean checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " don`t exist");
        } else {
            return true;
        }
    }
}
