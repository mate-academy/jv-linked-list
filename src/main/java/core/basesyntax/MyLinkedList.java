package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail = new Node<>(tail, value, null);
            tail.previous.next = tail;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(null, value, head);
            head.next.previous = head;
            size++;
        } else {
            Node<T> actual = getNode(index);
            Node<T> newNode = new Node<>(actual.previous, value, actual);
            newNode.previous.next = newNode;
            newNode.next.previous = newNode;
            size++;
        }
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
        checkIndex(index, size);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> element = getNode(index);
        Node<T> next = element.next;
        Node<T> previous = element.previous;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
        }
        size--;
        return element.value;

    }

    @Override
    public boolean remove(T t) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if ((t == null && t == current.value) || (t != null && t.equals(current.value))) {
                remove(i);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        checkIndex(index, size - 1);
        Node<T> result;
        if (index > size / 2) {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.previous;
            }
        } else {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        }
        return result;
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private T value;

        Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
