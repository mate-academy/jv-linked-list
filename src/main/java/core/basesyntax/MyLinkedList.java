package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        link(newNode, last, null);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, true);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = (index == 0) ? first : getNode(index);
        Node<T> newNode = new Node<>(value, null, null);
        link(newNode, current.previous, current);
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);

        Node<T> node = getNode(index);

        return node.data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, false);
        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> current = getNode(index);
        final T removed = current.data;
        unlink(current);
        return removed;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            boolean match = (current.data == null && object == null)
                    || (current.data != null && current.data.equals(object));
            if (match) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private void grow() {
        size++;
    }

    private void shrink() {
        size--;
    }

    private Node<T> getNode(int index) {
        checkIndex(index, false);

        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private void checkIndex(int index, boolean allowEnd) {
        int max = allowEnd ? size : size - 1;
        if (index < 0 || index > max) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void link(Node<T> newNode, Node<T> prev, Node<T> next) {
        newNode.previous = prev;
        newNode.next = next;

        if (prev != null) {
            prev.next = newNode;
        } else {
            first = newNode;
        }

        if (next != null) {
            next.previous = newNode;
        } else {
            last = newNode;
        }

        grow();
    }

    private void unlink(Node<T> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            first = node.next;
        }

        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            last = node.previous;
        }

        shrink();
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        Node(T data, Node<T> next, Node<T> previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }
}
