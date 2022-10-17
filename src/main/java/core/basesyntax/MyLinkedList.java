package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node head;
    private Node tail;
    private Node currentNode;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node node = new Node(null, value, null);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node node = iterator(index);
        add((T) node.value);
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return iterator(index).value;
    }

    @Override
    public T set(T value, int index) {
        iterator(index).next.prev.value = value;
        return value;
    }

    @Override
    public T remove(int index) {
        unlink(iterator(index));
        size--;
        return iterator(index).value;
    }

    @Override
    public boolean remove(T object) {
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

    private Node<T> iterator(int index) {
        currentNode = head;
        while (index > 0) {
            if (head.next == null) {
                return head;
            }
            currentNode = head.next;
            index--;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        node.prev = null;
        node.next = null;
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev,T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
