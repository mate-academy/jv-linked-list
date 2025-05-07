package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        last = new Node<>(last, value, null);
        if (size == 0) {
            first = last;
        } else {
            last.prev.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size " + size);
        } else if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode;
        if (index == 0) {
            newNode = new Node<>(null, value, first);
            first = newNode;
            newNode.next.prev = newNode;
            size++;
            return;
        }
        Node<T> node = findNode(index);
        newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node: list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        return node == null ? null : node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        final T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (size == 1 || index == 0 || index + 1 == size) {
            return removeSpecialIndex(index);
        }
        Node<T> node = findNode(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.value == object
                    || (node.value != null && node.value.equals(object))) {
                remove(i);
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size " + size);
        }
    }

    private Node<T> findNode(int index) {
        Node<T> node = first;
        if (index < size / 2) {
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

    private T removeSpecialIndex(int index) {
        T removedValue = first.value;
        if (size == 1) {
            last = null;
            first = null;
        } else if (index == 0) {
            first = first.next;
            first.prev = null;
        } else {
            removedValue = last.value;
            last = last.prev;
            last.prev.next = null;
        }
        size--;
        return removedValue;
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
