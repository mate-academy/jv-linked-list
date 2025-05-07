package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index  " + index
                    + "is invalid for size " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> next = findNode(index);
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(prev, value, next);
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        next.prev = newNode;
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
        checkIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue = findNode(index).value;
        unLink(findNode(index));
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        int index = 0;
        while (index < size) {
            if ((node.value == object) || (node.value
                    != null && node.value.equals(object))) {
                unLink(node);
                size--;
                return true;
            }
            node = node.next;
            index++;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index  " + index
                    + "is invalid for size " + size);
        }
    }

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unLink(Node<T> node) {
        if (first == node) {
            first = first.next;
        } else if (last == node) {
            last = last.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
