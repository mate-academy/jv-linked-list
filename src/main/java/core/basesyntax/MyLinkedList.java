package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> element = new Node<>(null, value, null);
        if (head == null) {
            head = tail = element;
        } else {
            tail.next = element;
            element.prev = tail;
            tail = element;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> element;
        if (index == 0) {
            element = new Node<>(null, value, head);
            head.prev = element;
            head = element;
            size++;
        }
        if (index == size) {
            add(value);
        }
        element = head;
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            Node<T> current = head;
            int counter = 0;
            while (current != null) {
                if (counter == index) {
                    return current.value;
                }
                counter ++;
                current = current.next;
            }
        }
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
        return head == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(" <-> ");
            }
            current = current.next;
        }
        return sb.toString();
    }

    private static class Node<E> {
        E value;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
