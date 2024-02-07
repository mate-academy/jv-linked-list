package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> tempNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tempNode;
        } else {
            tail.next = tempNode;
            tempNode.prev = tail;
        }
        tail = tempNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index
                    + " for size: " + size);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> current = getNode(index);
            final Node<T> newNode = new Node<>(current.prev, value, current);
            if (current.prev == null) {
                head = newNode;
            } else {
                current.prev.next = newNode;
            }
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t:list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T finalValue = current.value;
        current.value = value;
        return finalValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T result = node.value;
        unlink(node);
        return result;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            Node<T> node = getNode(i);
            if (node.value == object || node.value != null && node.value.equals(object)) {
                unlink(node);
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

    public void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index
                    + " for size: " + size);
        }
        if (index < (size >> 1)) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
