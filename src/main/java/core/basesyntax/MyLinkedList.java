package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size = 0;
    Node<T> head;
    Node<T> tail;


    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else {
            Node<T> buffer = tail;
            Node<T> newNode = new Node<>(buffer, value, null);
            tail = newNode;
            buffer.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index){
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index);
        }
        Node<T> clone;
        Node<T> newNode;
        if (index < size() >> 1){
            clone = head;
            for (int i = 0; i < index; ++i){
                clone = clone.next;
            }
        }else {
            clone = tail;
            for (int i = size() - 1; i > index; --i){
                clone = clone.prev;
            }
        }
        newNode = new Node<>(clone.prev, value, clone);
        clone.prev.next = newNode;
        clone.prev = newNode;
        size++;

    }

    @Override
    public void addAll(List<T> list) {

    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(index);
        }
        Node<T> wantedElement;
        if (index < size() >> 1) {
            wantedElement = head;
            for (int i = 0; i < index; ++i) {
                wantedElement = wantedElement.next;
            }
        } else {
            wantedElement = tail;
            for (int i = size - 1; i > index; --i) {
                wantedElement = wantedElement.prev;
            }
        }
        return wantedElement.value;
    }

    @Override
    public void set(T value, int index) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public T remove(T t) {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

    }
}
