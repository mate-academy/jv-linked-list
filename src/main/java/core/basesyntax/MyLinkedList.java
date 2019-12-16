package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private class Node<T> {

        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.value = item;
        }

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(value);
            tail = head;
            size++;
            return;
        }
        tail = new Node<>(value, null, tail);
        tail.prev.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            Node newNode = new Node(value, node.prev, node);
            node.prev = newNode;
            newNode.prev.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return head.value;
        }
        Node<T> target = head.next;

        for (int i = 1; i < index; i++) {
            target = target.next;
        }
        return target.value;
    }

    @Override
    public void set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        searchIndex(index).value = value;
    }

    private Node<T> searchIndex(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node node = searchIndex(index);
        if (size == 1) {
            head = tail = null;
            size--;
            return (T) node;
        }
        T removed = (T) get(index);
        if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return (T) removed;
    }

    @Override
    public T remove(T t) {
        Node<T> toRemove = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(t, toRemove.value)) {
                return remove(i);
            }
            toRemove = toRemove.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
