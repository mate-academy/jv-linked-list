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
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            Node<T> lastNode = lastNode();
            new Node<T>(lastNode, value, null);
            tail = lastNode.next;
        }
    }

    @Override
    public void add(T value, int index) {
        if (checkIndex(index)) {
            Node<T> currentNode = searchElement(index);
            new Node<T>(currentNode.prev, value, currentNode);
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
            delNode.prev = delNode.next;
            delNode.next = delNode.prev;
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
        Node<T> currentNode = head;
        while (currentNode != null) {
            size++;
            currentNode = currentNode.next;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() > 0;
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
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (index == size) {
                break;
            }
            size++;
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
            currentNode = currentNode.next;
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
