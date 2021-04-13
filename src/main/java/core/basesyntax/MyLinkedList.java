package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.item = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        if (head == null && tail == null) {
            Node<T> newNod = new Node<>(null, value, null);
            head = newNod;
            tail = newNod;
        } else {
            Node<T> someNod = new Node<>(tail, value, null);
            tail.next = someNod;
            tail = someNod;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> perAddNod = search(index);
            Node<T> addNode = new Node<>(perAddNod.prev, value, perAddNod);
            perAddNod.prev.next = addNode;
            perAddNod.prev = addNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        Node<T> search = search(index);
        return search.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = search(index);
        T returnValue = setNode.item;
        setNode.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = search(index);
        if (index == 0) {
            if (size == 1) {
                head = null;
                tail = null;
            }
            head = removeNode.next;
        } else if (removeNode.next != null) {
            removeNode.prev.next = removeNode.next;
            removeNode.next.prev = removeNode.prev;
        }

        size--;
        return removeNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
                remove(i);
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
        return size == 0;
    }

    private void chekIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private Node<T> search(int index) {
        chekIndex(index);
        int i = 0;
        Node<T> search = head;
        while (search != null) {
            if (index == i) {
                return search;
            }
            search = search.next;
            i++;
        }
        return null;
    }
}
