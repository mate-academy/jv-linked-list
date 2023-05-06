package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = new Node<>(null, null, null);
        tail = head;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirst(value);
        } else {
            addAfter(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (isEmpty() && index == 0) {
            addFirst(value);
        } else if (index == 0) {
            addBefore(value);
        } else if (index == size) {
            addAfter(value);
        } else {
            link(node(index), value);
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
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        verifyIndexIsInRange(index);
        Node<T> element = node(index);
        T oldValue = element.item;
        element.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        verifyIndexIsInRange(index);
        Node<T> element = node(index);
        if (index == 0) {
            element.next.prev = null;
            head = element.next;
        } else if (index == size - 1) {
            element.prev.next = null;
            tail = element.prev;
        } else {
            element.prev.next = element.next;
            element.next.prev = element.prev;
        }
        size--;
        return element.item;
    }

    @Override
    public boolean remove(T object) {
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

    private Node<T> node(int index) {
        verifyIndexIsInRange(index);
        Node<T> node;
        if (index < size >> 1) {
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

    private void addFirst(T value) {
        head.item = value;
    }

    private void addBefore(T value) {
        head.prev = new Node<>(null, value, head);
        head = head.prev;
    }

    private void addAfter(T value) {
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
    }

    private void link(Node<T> node, T value) {
        node.prev.next = new Node<>(node.prev, value, node);
        node.prev = node.prev.next;
    }

    private void unlink(Node<T> node) {

    }


    private void verifyIndexIsInRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private static class Node<T> {
        Node<T> prev;
        T item;
        Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
