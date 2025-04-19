package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> node = new Node<>(value);
        if (size == 0) {
            head = tail = node;
            size++;
            return;
        }
        if (index == 0) {
            node.setNext(head);
            head.setPrev(node);
            node.setPrev(null);
            head = node;
            size++;
            return;
        }
        if (index == size) {
            node.setNext(null);
            node.setPrev(tail);
            tail.setNext(node);
            tail = node;
        } else {
            Node<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            node.setNext(temp.getPrev().getNext());
            node.setPrev(temp.getPrev());
            temp.setPrev(node);
            node.getPrev().setNext(node);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T nodka : list) {
            add(nodka);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node.getItem();
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        T old = temp.getItem();
        temp.setItem(value);
        return old;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        T removed = temp.getItem();
        if (temp == tail && temp == head) {
            head = null;
            tail = null;
            size--;
            return removed;
        }
        if (temp == head) {
            head.getNext().setPrev(null);
            head = head.getNext();
            size--;
            return removed;
        }
        if (temp == tail) {
            tail.getPrev().setNext(null);
            tail = tail.getPrev();
            size--;
            return removed;
        }
        temp.getPrev().setNext(temp.getNext());
        temp.getNext().setPrev(temp.getPrev());
        size--;
        return removed;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((node.getItem() != null && node.getItem().equals(object))
                    || (node.getItem() == null && object == null)) {
                remove(i);
                return true;
            }
            node = node.getNext();
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
}
