package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String indexOutOfBoundNode = "While looking for Node"
            + "Index is out of bound %d, Size %d";
    private static final String indexOutOfBoundAdd = "While adding item"
            + "we got Index is out of bound %d, Size %d";

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            tail(value);
        } else {
            Node<T> next = getNode(index);
            Node<T> prev = next.prev;
            Node<T> newNode = new Node<>(prev, value, next);
            next.prev = newNode;
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T nodes : list) {
            add(nodes);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> link = getNode(index);
        T oldValue = link.item;
        link.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T value) {
        Node<T> current = head;
        while (current != null) {
            if (value == null ? current.item == null : value.equals(current.item)) {
                unlink(current);
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

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T item;

        private Node(Node<T> before, T element, Node<T> after) {
            item = element;
            next = after;
            prev = before;
        }
    }

    private void tail(T next) {
        Node<T> tailLink = tail;
        final Node<T> newNode = new Node<>(tailLink, next, null);
        tail = newNode;
        if (tailLink == null) {
            head = newNode;
        } else {
            tailLink.next = newNode;
        }
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        T data = node.item;
        node.item = null;
        size--;
        return data;
    }

    private Node<T> getNode(int index) {
        checkIndexForGetNode(index);
        Node<T> current;
        if (index < (size >> 1)) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndexForGetNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(indexOutOfBoundNode);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(indexOutOfBoundAdd);
        }
    }
}
