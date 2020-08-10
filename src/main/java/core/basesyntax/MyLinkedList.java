package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> currentLast = this.last;
        Node<T> newLast = new Node<>(value, currentLast, null);
        this.last = newLast;
        if (currentLast == null) {
            first = newLast;
        } else {
            currentLast.next = newLast;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        isValidIndex(index);
        Node<T> current = find(index);
        Node<T> prev = current.prev;
        Node<T> newNode = new Node<>(value, prev, current);
        current.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
        return true;
    }

    @Override
    public T get(int index) {
        isValidIndex(index);
        return find(index).item;
    }

    @Override
    public T set(T value, int index) {
        isValidIndex(index);
        Node<T> current = find(index);
        T itemToReturn = current.item;
        current.item = value;
        return itemToReturn;
    }

    @Override
    public T remove(int index) {
        isValidIndex(index);
        Node<T> current = find(index);
        return removeNode(current);
    }

    @Override
    public boolean remove(T t) {
        for (Node<T> current = first; current != null; current = current.next) {
            if (t != null ? t.equals(current.item) : current.item == t) {
                removeNode(current);
                return true;
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

    private class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(T element, Node<T> prev, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> find(int index) {
        if (index < (size / 2)) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        Node<T> current = last;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void isValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private T removeNode(Node<T> current) {
        Node<T> next = current.next;
        Node<T> prev = current.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            current.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            current.next = null;
        }
        T toReturn = current.item;
        current.item = null;
        size--;
        return toReturn;
    }
}
