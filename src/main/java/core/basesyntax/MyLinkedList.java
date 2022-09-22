package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

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
        for (T l : list) {
            add(l);
        }
    }

    @Override
    public T get(int index) {
        checkIfIndexIsOutOfBounds(index);
        Node<T> current = getNodeByIndex(index);
        return current.element;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexIsOutOfBounds(index);

        Node<T> current = getNodeByIndex(index);
        T oldValue = current.element;
        current.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexIsOutOfBounds(index);
        return unlink(getNodeByIndex(index)).element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        if (object == null) {
            for (int i = 0; current != null; i++, current = current.next) {
                if (current.element == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (current.element.equals(object)) {
                    unlink(current);
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

    public void checkIfIndexIsOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(T element) {
            this.element = element;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> unlink(Node<T> node) {
        Node<T> removedNode = null;
        Node<T> current = head;
        if (node == head) {
            removedNode = head;
            head = head.next;
        }
        if (node == tail) {
            removedNode = tail;
            tail.prev = tail;
            tail.next = null;
        } else {
            for (int i = 0; i < size; i++) {
                current = current.next;
                if (node.equals(current)) {
                    removedNode = current;
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
            }
        }
        size--;
        return removedNode;
    }
}

