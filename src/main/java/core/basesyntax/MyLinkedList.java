package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        private T getItem() {
            return item;
        }

        private void setItem(T item) {
            this.item = item;
        }

        private Node<T> getPrev() {
            return prev;
        }

        private void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        private Node<T> getNext() {
            return next;
        }

        private void setNext(Node<T> next) {
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == size) {
            linkLast(value);
        } else if (index == 0) {
            linkFirst(value);
        } else {
            Node<T> succ = node(index);
            linkBefore(value, succ);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        for (T t : list) {
            linkLast(t);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);

        return node(index).getItem();
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);

        Node<T> x = node(index);
        T old = x.getItem();
        x.setItem(value);

        return old;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);

        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.getNext()) {
            if (object == null ? x.getItem() == null : object.equals(x.getItem())) {
                unlink(x);
                return true;
            }
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

    private void linkFirst(T e) {
        Node<T> f = head;
        Node<T> newNode = new Node<>(e, null, f);
        head = newNode;

        if (f == null) {
            tail = newNode;
        } else {
            f.setPrev(newNode);
        }

        size++;
    }

    private void linkLast(T e) {
        Node<T> l = tail;
        Node<T> newNode = new Node<>(e, l, null);
        tail = newNode;

        if (l == null) {
            head = newNode;
        } else {
            l.setNext(newNode);
        }

        size++;
    }

    private void linkBefore(T e, Node<T> succ) {
        Node<T> pred = succ.getPrev();
        Node<T> newNode = new Node<>(e, pred, succ);
        succ.setPrev(newNode);
        if (pred == null) {
            head = newNode;
        } else {
            pred.setNext(newNode);
        }

        size++;
    }

    private T unlink(Node<T> x) {
        Node<T> next = x.getNext();
        Node<T> prev = x.getPrev();

        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
            x.setPrev(null);
        }

        if (next == null) {
            tail = prev;
        } else {
            next.setPrev(prev);
            x.setNext(null);
        }

        T element = x.getItem();
        x.setItem(null); // help GC
        size--;

        return element;
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> x = head;

            for (int i = 0; i < index; i++) {
                x = x.getNext();
            }

            return x;
        } else {
            Node<T> x = tail;

            for (int i = size - 1; i > index; i--) {
                x = x.getPrev();
            }

            return x;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
