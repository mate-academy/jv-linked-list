package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> last = tail;
        Node<T> newNode = new Node<>(tail, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        isIndexValid(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = getNode(index);
        Node<T> previous = node.previous;
        Node<T> newNode = new Node<>(previous, value, node);
        node.previous = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    public Node<T> checkIndex(int index) {
        if (0 <= index && index < size) {
            return getNode(index);
        } else {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    @Override
    public T get(int index) {
        return checkIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = checkIndex(index);
        T old = node.item;
        node.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        Node<T> node = checkIndex(index);
        T value = node.item;
        unlink(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node != null) {
            unlink(node);
            return true;
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
        Node<T> node;
        if (index < (size >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private Node<T> getNode(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if ((node.item == object) || object != null && object.equals(node.item)) {
                return node;
            }
        }
        return null;
    }

    private void isIndexValid(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException(" ");
        }
    }

    private void unlink(Node<T> node) {
        if (node.previous == null) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }
        if (node.next == null) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.previous = prev;
            this.item = item;
            this.next = next;
        }
    }
}
