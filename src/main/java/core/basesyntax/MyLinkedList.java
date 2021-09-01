package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = addNode;
            size++;
            return;
        }
        tail.next = addNode;
        tail = addNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index invalid");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = getNode(index);
        Node<T> addNode = new Node<>(node.prev, value, node);
        if (index == 0) {
            head = addNode;
            size++;
            return;
        }
        node.prev.next = addNode;
        node.prev = addNode;
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
        checkIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T changedElement = node.element;
        node.element = value;
        return changedElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.element == object || (node.element != null && node.element.equals(object))) {
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index invalid");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        int counter;
        if (index > size / 2) {
            counter = size - 1;
            node = tail;
            while (index < counter) {
                node = node.prev;
                counter--;
            }
        } else {
            counter = 0;
            node = head;
            while (counter < index) {
                node = node.next;
                counter++;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        if (node.prev == null) {
            if (size == 1) {
                size--;
                return node.element;
            }
            node.next.prev = null;
            head = node.next;
            size--;
            return node.element;
        }
        if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
            size--;
            return node.element;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.element;
    }
}

