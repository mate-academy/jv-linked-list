package core.basesyntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tale;
    public class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public MyLinkedList() {}

    private void insertFirst(T value) {
            Node<T> node = new Node<>(value);
            node.setPrev(null);
            node.setNext(null);
            head = node;
            tale = node;
            size++;
    }

    private void insertLast(T value) {
        Node<T> node = new Node<>(value);
        tale.setNext(node);
        node.setPrev(tale);
        node.setNext(null);
        tale = node;
        size++;
    }
    private void insertHead(T value) {
        Node<T> node = new Node<>(value);
        Node<T> nextNode = head;
        node.setPrev(null);
        node.setNext(nextNode);
        nextNode.setPrev(node);
        head = node;
        size++;
    }

    private void insert(Node<T> tNode, T value) {
        Node<T> previousNode = tNode.getPrev();
        Node<T> node = new Node<>(value);
        previousNode.next = node;
        node.prev = previousNode;
        node.next = tNode;
        tNode.prev = node;
        size++;
    }

    private void iterator(int index, T value) {
        if (index < size - index) insert(headIterator(index), value);
        else insert(tailIterator(index), value);
    }

    private Node<T> headIterator(int index) {
        Node<T> tNode = head;
        for (int i = 0; i < index; i++) {
            tNode = tNode.getNext();
        }
        return tNode;
    }

    private Node<T> tailIterator(int index) {
        Node<T> tNode = tale;
        for (int i = 0; i < size - index - 1; i++) {
            tNode = tNode.getPrev();
        }
        return tNode;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) insertFirst(value);
        else insertLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) insertLast(value);
        else if (index == 0) insertHead(value);
        else iterator(index, value);
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
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

    @Override
    public String toString() {
        List<T> list = new ArrayList<>();
        Node<T> tNode = head;
        for (int i = 0; i < size; i++) {
            list.add(tNode.value);
            tNode = tNode.getNext();
        }
        return list.toString();
    }
}
