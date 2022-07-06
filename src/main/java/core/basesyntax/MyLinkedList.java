package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " not found.");
        }
        if (index == size) {
            addLast(value);
        } else {
            addBefore(value, findNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            addLast(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> element = findNode(index);
        T oldVal = element.item;
        element.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (Objects.equals(node.item, object)) {
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void addLast(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    private void addBefore(T value, Node<T> beforeNode) {
        Node<T> pred = beforeNode.prev;
        Node<T> newNode = new Node<>(pred, value, beforeNode);
        beforeNode.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private Node<T> findNode(int index) {
        if (index < (size >> 1)) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private T unlink(Node<T> x) {
        final T element = x.item;
        Node<T> next = x.next;
        Node<T> prev = x.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        size--;
        x.item = null;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " not found.");
        }
    }
}
