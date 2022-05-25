package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value);
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            addAfter(current, value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("No such index");
        }
        if (size == 0 || index == 0) {
            addFirst(value);
            return;
        }
        Node<T> current = head;
        for (int i = 1; i < index; i++) {
            current = current.next;
        }
        addAfter(current, value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T values : list) {
            add(values);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = getNodeByIndex(index).item;
        getNodeByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue = getNodeByIndex(index).item;
        unlink(getNodeByIndex(index));
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; current != null; current = current.next, i++) {
            if ((current.item == object) || (current.item != null && current.item.equals(object))) {
                unlink(getNodeByIndex(i));
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("No such index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index <= size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node.next == null && node.prev == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = node.next;
        } else if (node.next == null) {
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        if (head != null) {
            head.prev = newNode;
        } else {
            tail = newNode;
        }
        head = newNode;
        size++;
    }

    private void addAfter(Node<T> current, T value) {
        Node<T> newNode = new Node<>(current, value, current.next);
        if (current.next == null) {
            tail = newNode;
        } else {
            current.next.prev = newNode;
        }
        current.next = newNode;
        size++;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
