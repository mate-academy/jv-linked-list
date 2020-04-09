package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            tail = head;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = new Node<>(value);
        if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
        } else {
            Node<T> current = getNode(head, index);
            Node<T> previous = current.prev;
            previous.next = node;
            node.prev = previous;
            node.next = current;
            current.prev = node;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(head, index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = getNode(head, index);
        final T replacedValue = current.value;
        current.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = getNode(head, index);
        if (current.equals(head)) {
            head = current.next;
        }
        if (current.equals(tail)) {
            tail = current.prev;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        }
        if (current.prev != null) {
            current.prev.next = current.next;
        }
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (current.value == t || current.value != null
                    && current.value.equals(t)) {
                remove(index);
                return true;
            }
            current = current.next;
            index++;
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

    public Node<T> getNode(Node<T> head, int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }
}
