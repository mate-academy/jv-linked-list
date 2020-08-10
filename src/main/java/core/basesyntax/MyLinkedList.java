package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            Node<T> node = new Node<>(value, null, null);
            head = node;
            tail = node;
        } else {
            Node<T> node = new Node<>(value, null, tail);
            tail.next = node;
            tail = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value, head, null);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(value, node, node.prev);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isValidIndex(index);
        Node<T> current = getNode(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T t) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (t != null ? t.equals(current.value) : current.value == t) {
                unlink(current);
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
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        int i = 0;
        Node<T> node = head;
        while (i < index) {
            node = node.next;
            i++;
        }
        return node;
    }

    private void isValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private T unlink(Node<T> current) {
        Node<T> next = current.next;
        Node<T> prev = current.prev;
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
        T toReturn = current.value;
        current.value = null;
        size--;
        return toReturn;
    }
}
