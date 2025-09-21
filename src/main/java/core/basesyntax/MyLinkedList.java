package core.basesyntax;

import java.util.List;
import java.util.Objects;


public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        Node<T> prev;
        Node<T> next;
        T value;

        Node(T value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;

        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        Node<T> current = head;

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index" + index + ", Size:" + size);
        }
        if (size == 0) {
            head = tail = newNode;
            size++;
            return;
        }
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }


    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List cannot be null");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = head;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("List cannot be empty");
        }
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = head;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("List cannot be empty");
        }
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = head;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("List cannot be empty;");
        }
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }

        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {

        Node<T> current = head;

        while (current != null) {
            // sprawdzenie wartości
            if (Objects.equals(object, current.value)) {

                if (current.prev == null) {
                    head = current.next;
                    if (head != null) {
                        head.prev = null;
                    }
                } else if (current.next == null) {
                    tail = current.prev;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }

                size--;
                return true;
            }

            current = current.next; // przesuwamy się dopiero po aktualizacji wskaźników
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

