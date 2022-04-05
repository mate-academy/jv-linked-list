package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> previous;

        Node(T item) {
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size && size == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            linkLast(value);
            return;
        }
        verifyIndex(index);
        Node<T> current = findNode(index);
        Node<T> pred = current.previous;
        Node<T> newNode = new Node<T>(value);
        newNode.previous = pred;
        newNode.next = current;
        current.previous = newNode;
        if (pred == null)
            head = newNode;
        else
            pred.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T position: list) {
            add(position);
        }
    }

    @Override
    public T get(int index) {
        verifyIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        verifyIndex(index);
        Node<T> sett = findNode(index);
        T oldValue = sett.item;
        sett.item = value;
        return oldValue;
        }

    @Override
    public T remove(int index) {
        verifyIndex(index);
        Node<T> removedNode = findNode(index);
        unlink(findNode(index));
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
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

    private void verifyIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid");
        }
    }

    private void linkFirst(T e) {
        final Node<T> f = head;
        final Node<T> newNode = new Node<> (e);
        head = newNode;
        head.next = f;
        if (f == null)
            tail = newNode;
        else
            f.previous = newNode;
        size++;
    }

    void linkLast(T value) {
        final Node<T> l = tail;
        final Node<T> newNode = new Node<>(value);
        tail = newNode;
        newNode.previous = l;
        if (l == null)
            tail = newNode;
        else
            l.next = newNode;
        size++;
    }

    T unlink(Node<T> deletedNode) {
        final T element = deletedNode.item;
        final Node<T> next = deletedNode.next;
        final Node<T> prev = deletedNode.previous;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            deletedNode.previous = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
            deletedNode.next = null;
        }
        size--;
        return element;
    }

    Node<T> findNode(int index) {
        if (index < (size >> 1)) {
            Node<T> target = head;
            for (int i = 0; i < index; i++)
                target = target.next;
            return target;
        } else {
            Node<T> target = tail;
            for (int i = size - 1; i > index; i--)
                target = target.previous;
            return target;
        }
    }
}
