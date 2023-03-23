package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    private static class Node<T> {
        private Node<T> next;
        private T value;

        Node(T value) {
            this.value = value;
            this.next = null;
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> prev = getNode(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> node = getNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        T value;
        if (index == 0) {
            value = head.value;
            head = head.next;
        } else {
            Node<T> prev = getNode(index - 1);
            Node<T> node = prev.next;
            value = node.value;
            prev.next = node.next;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            Node<T> prev = null;
            Node<T> curr = head;
            while (curr != null) {
                if (curr.value == null) {
                    if (prev == null) {
                        head = curr.next;
                    } else {
                        prev.next = curr.next;
                    }
                    size--;
                    return true;
                }
                prev = curr;
                curr = curr.next;
            }
        } else {
            Node<T> prev = null;
            Node<T> curr = head;
            while (curr != null) {
                if (curr.value != null && curr.value.equals(object)) {
                    if (prev == null) {
                        head = curr.next;
                    } else {
                        prev.next = curr.next;
                    }
                    size--;
                    return true;
                }
                prev = curr;
                curr = curr.next;
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

    private Node<T> getNode(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
