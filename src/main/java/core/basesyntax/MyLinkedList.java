package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    static class Node<T> {
        private T element;
        private Node<T> previos;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
            this.previos = null;
            this.next = null;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.previos = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<T> newNode = new Node<>(value);
        Node<T> current = head;
        if (index == 0) {
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.previos = newNode;
                head = newNode;
            }
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        newNode.next = current;
        newNode.previos = current.previos;
        current.previos.next = newNode;
        current.previos = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T data : list) {
            Node<T> newNode = new Node<>(data);
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.previos = tail;
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<T> current = head;
        T oldElement;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        oldElement = current.element;
        current.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (current.previos != null) {
            current.previos.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.previos = current.previos;
        } else {
            tail = current.previos;
        }
        current.next = null;
        current.previos = null;
        T oldElement = current.element;
        size--;
        return oldElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null && current.element == null
                    || object != null && object.equals(current.element)) {
                if (current.previos != null) {
                    current.previos.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.previos = current.previos;
                } else {
                    tail = current.previos;
                }
                current.next = null;
                current.previos = null;
                size--;
                return true;
            }
            current = current.next;
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
