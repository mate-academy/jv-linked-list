package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {}

    public MyLinkedList(Node<T> head, Node<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public void add(T value) {
        new Node<T> (head, value, tail);
    }

    @Override
    public void add(T value, int index) {
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

    private void unlink(Node<T> node){
        node.next.prev = node.prev;
        node.prev.next = node.next;

    }

     private class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node( Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
