package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        private Node(T element) {
            this.element = element;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        }
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = getNodeByIndex(index);
            newNode.next = current;
            current.prev.next = newNode;
            newNode.prev = current.prev;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list.isEmpty()) {
            throw new NoSuchElementException("The list is empty");
        }
        for (T l : list) {
            add(l);
        }
    }

    @Override
    public T get(int index) {
        if (checkIfIndexIsOutOfBounds(index)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> current = getNodeByIndex(index);
        return current.element;
    }

    @Override
    public T set(T value, int index) {
        if (checkIfIndexIsOutOfBounds(index)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        T oldValue;
        Node<T> current = getNodeByIndex(index);
        oldValue = current.element;
        current.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (checkIfIndexIsOutOfBounds(index)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return unlink(index).element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        if (object == null) {
            for (int i = 0; current != null; i++, current = current.next) {
                if (current.element == null) {
                    unlink(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (current.element.equals(object)) {
                    unlink(i);
                    return true;
                }
                current = current.next;
            }
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

    public boolean checkIfIndexIsOutOfBounds(int index) {
        return index < 0 || index >= size;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> unlink(int index) {
        Node<T> removedNode = null;
        Node<T> current = head;
        if (index == 0) {
            removedNode = head;
            head = head.next;
        }
        if (index == size - 1) {
            removedNode = tail;
            tail.prev = tail;
            tail.next = null;
        }
        if (index != 0 && index != size - 1) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            removedNode = current;
            current.prev.next = current.next;
            current.next.prev = current.prev;

        }
        size--;
        return removedNode;
    }
}

