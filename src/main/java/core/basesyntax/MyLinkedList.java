package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    void headElement(T element) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(null, element, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    void tailElement(T element) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, element, null);
        tail = newNode;
        if (last == null){
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    void insertElementBefore(T element, Node<T> successor) {
        final Node<T> pred = successor.prev;
        final Node<T> newNode = new Node<>(pred,element, successor);
        successor.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
            size++;
        }

    }

    @Override
    public void add(T value) {
        tailElement(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size)
            tailElement(value);
        else
            insertElementBefore(value, searchElement(index));
    }

    @Override
    public void addAll(List<T> list) {
        Object[] elements = list.toArray();
        if (elements.length != 0) {
            Node<T> pred, successor;
            successor = null;
            pred = tail;
            for (Object element : elements) {
                T e = (T) element;
                Node<T> newNode = new Node<>(pred, e, null);
                if (pred == null) {
                    head = newNode;
                } else {
                    pred.next = newNode;
                }
                size += elements.length;
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) searchElement(index);
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
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
        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 && index > size) {
            throw new IndexOutOfBoundsException("Index: "+ index  +" of bounds for size : "+ size);
        }
    }
    private Node<T> searchElement(int index) {
        if (index < (size >> 1)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++)
                current = current.next;
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--)
                current = current.prev;
            return current;
        }
    }
}
