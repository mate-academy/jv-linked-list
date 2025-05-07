package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> firstNode = new Node<>(null, value,null);
            tail = firstNode;
            head = firstNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexForAddMethod(index);
        Node<T> next = searchNodeByIndex(index);
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(prev, value, next);
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = searchNodeByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T replacedElement;
        Node<T> current = searchNodeByIndex(index);
        replacedElement = current.item;
        current.item = value;
        return replacedElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = searchNodeByIndex(index);
        final T element = current.item;
        Node<T> prev = current.prev;
        Node<T> next = current.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            current.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            current.next = null;
        }
        current.item = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == object || current.item.equals(object)) {
                remove(i);
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

    private void checkIndexForAddMethod(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
        }
    }

    private Node<T> searchNodeByIndex(int index) {
        Node<T> current;
        if (size / 2 > index) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = 0; i < size - index - 1; i++) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        private Node(Node<E> prev,E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
