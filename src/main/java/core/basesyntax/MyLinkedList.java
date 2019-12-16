package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size = 0;
    Node<T> head;
    Node<T> tail;


    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return;
        }
        tail = new Node<>(tail, value, null);
        tail.prev.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index){
            add(value);
            return;
        }
        Node<T> clone = returnNodeByIndex(index);
        Node<T> newNode = new Node<>(clone.prev, value, clone);
        clone.prev.next = newNode;
        clone.prev = newNode;
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (T i : list){
            add(i);
        }
    }

    @Override
    public T get(int index) {
        return returnNodeByIndex(index).value;
    }

    @Override
    public void set(T value, int index) {
        returnNodeByIndex(index).value = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
        if (index == 0){
            T value = head.value;
            head = head.next;
            head.prev = null;
            size--;
            return value;
        }
        if (index == size - 1){
            T value = tail.value;
            tail = tail.prev;
            tail.next = null;
            size --;
            return value;
        }
        Node<T> willBeRemove = returnNodeByIndex(index);
        willBeRemove.prev.next = willBeRemove.next;
        willBeRemove.next.prev = willBeRemove.prev;
        size--;
        return willBeRemove.value;
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

    private Node<T> returnNodeByIndex(int index){
        if (index < 0 || index >= size) {
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
        return wantedElement;
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
