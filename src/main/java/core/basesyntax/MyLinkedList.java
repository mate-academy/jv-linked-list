package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(last, value, null);
        if (isEmpty()) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node;
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = findIndex(index);
        if (newNode.prev == null) {
            node = new Node<>(null, value, first);
            first.prev = node;
            first = node;
        } else {
            node = new Node<>(newNode.prev, value, newNode);
            newNode.prev.next = node;
            newNode.prev = node;
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
        return findIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> findNode = findIndex(index);
        T deletedNode = findNode.item;
        findNode.item = value;
        return deletedNode;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object == node.item || object != null && object.equals(node.item)) {
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

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            first = node.next;
        } else if (node.next == null) {
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private boolean checkIndex(int index) {
        if (index >= 0 && index <= size) {
            return true;
        } else {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private Node<T> findIndex(int index) {
        if (index == size || !checkIndex(index)) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> node;
        if (index <= size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
