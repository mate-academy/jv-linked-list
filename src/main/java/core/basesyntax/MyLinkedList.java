package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null,value,null);
        if (isEmpty()) {
            addFirst(newNode);
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next= null;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("LinkedList index: " + index + " out of bounds exception");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> indexVariable = head;
            for (int i = 0; i < index; i++) {
                indexVariable = indexVariable.next;
            }
            Node<T> pred = indexVariable.prev;
            Node<T> newNode = new Node<>(pred, value, indexVariable);
            indexVariable.prev = newNode;
            if (pred == null) head = newNode;
            else pred.next = newNode;
            size++;
        }
        //newNode.next = indexVariable.next;
        //newNode.prev = indexVariable;
        //indexVariable.next = newNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> indexNode = head;
        for (int i  = 0; i < index; i++) {
            indexNode = indexNode.next;
        }
        return indexNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> indexNode = head;
        for (int i  = 0; i < index; i++) {
            indexNode = indexNode.next;
        }
        T result = indexNode.item;
        indexNode.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> x = head;
        while (x != null) {
            if ((x.item == object) || (x.item != null && x.item.equals(object))) {
                unlink(x);
                return true;
            }
            x = x.next;
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

    private void addFirst(Node<T> node) {
        head = node;
        tail = node;
        size++;
    }

    private Node<T> findIndex(int index) {
        Node<T> indexNode = head;
        for (int i  = 0; i < index; i++) {
            indexNode = indexNode.next;
        }
        return indexNode;
    }

    private T unlink (Node<T> node) {
        T element = node.item;
        if (node.prev == null && node.next == null) {
            node.item = null;
            size--;
        }
        if (node.prev != null && node.next == null){
            tail = node.prev;
            node.prev.next = null;
            size--;
        }
        if (node.prev == null && node.next != null) {
            head = node.next;
            node.next.prev = null;
            size--;
        }
        if (node.prev != null && node.next != null){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("LinkedList index: " + index + " out of bounds exception");
        }
    }
    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.item = value;
            this.next = next;
        }
    }
}
