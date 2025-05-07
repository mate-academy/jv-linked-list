package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value);
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            Node<T> node = new Node<>(prev, value, prev.next);
            prev.next.prev = node;
            prev.next = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T result = node.value;
        node.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (object == node.value || object != null && object.equals(node.value)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void addFirst(T value) {
        head = tail = new Node<>(null, value, null);
    }

    private void addLast(T value) {
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> node = head;
            for (int i = 0; i != index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i != index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private void unlink(Node<T> node) {
        if (tail == head) {
            tail = head = null;
        } else if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Particular index :"
                    + index + " is out of bounds for the size");
        }
    }

    private static class Node<E> {
        private Node<E> prev;
        private Node<E> next;
        private E value;

        private Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
