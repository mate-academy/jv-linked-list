package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T item) {
        Node<T> add;
        if (this.size != 0) {
            add = new Node<>(item, this.tail, null);
            this.tail.next = add;
        } else {
            add = new Node<>(item, null, null);
            this.head = add;
        }
        this.tail = add;

        size++;
        return true;
    }

    @Override
    public void add(T item, int index) {
        if (index == this.size) {
            add(item);
            return;
        }
        Node<T> willNext = getNode(index);
        Node<T> prev = willNext.prev;
        Node<T> add = new Node<>(item, prev, willNext);
        if (prev == null) {
            this.head = add;
        } else {
            prev.next = add;
        }
        willNext.prev = add;
        size++;
    }

    @Override
    public boolean addAll(List<T> items) {
        for (T item : items) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T set, int index) {
        Node<T> request = getNode(index);
        T item = request.item;
        request.item = set;
        return item;
    }

    @Override
    public T remove(int index) {
        Node<T> request = getNode(index);
        if (request.next == null) {
            this.tail = request.prev;
        } else if (request.prev == null) {
            this.head = request.next;
        } else {
            request.prev.next = request.next;
            request.next.prev = request.prev;
        }
        size--;
        return request.item;
    }

    @Override
    public boolean remove(T item) {
        for (int i = 0; i < this.size; i++) {
            Node<T> request = getNode(i);
            if (request.item == item || request.item.equals(item) && request.item != null) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        indexException(index);
        if (index <= this.size / 2) {
            return searchFromHead(index);
        } else {
            return searchFromTail(index);
        }
    }

    private Node<T> searchFromHead(int index) {
        Node<T> request = this.head;
        for (int i = 0; i < index; i++) {
            request = request.next;
        }
        return request;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> request = this.tail;
        for (int i = this.size - 1; i > index; i--) {
            request = request.prev;
        }
        return request;
    }

    private void indexException(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Sir, your index is out of bounds");
        }
    }

}
