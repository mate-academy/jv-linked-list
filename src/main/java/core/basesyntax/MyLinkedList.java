package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(value);
            tail = head;
            head.next = tail;
            //tail.prev = head;} else if (size == 1) {
            Node insertedElement = new Node(value);
            head.next = insertedElement;
            tail = insertedElement;
            insertedElement.prev = head;
        } else {
            Node insertedElement = new Node(value);
            insertedElement.prev = tail;
            tail.next = insertedElement;
            tail = insertedElement;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        verifyInBound();
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("incorrect index: " + index);
        } else {
            Node<T> element = head;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
            return element.value;
        }
    }

    @Override
    public T set(T value, int index) {
        if (isEmpty() || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("incorrect index: " + index);
        } else if (index > 0 || index < size) {
            Node<T> oldElement = head;
            for (int i = 0; i < index; i++) {
                oldElement = oldElement.next;
            }
            Node<T> insertedElement = new Node<>(value);
            insertedElement.prev = oldElement.prev;
            insertedElement.next = oldElement.next;
            oldElement.prev.next = insertedElement;
            oldElement.next.prev = insertedElement;
            return oldElement.value;
        }
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

    private void verifyInBound() {

    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> prev) {
            this.value = value;
            this.prev = prev;
        }

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
            prev.next = this;
            next.prev = this;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
