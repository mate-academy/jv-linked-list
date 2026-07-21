package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);

        if (last == null) {
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
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + ", size: " + size);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = findNodeByIndex(index);
        Node<T> previous = current.prev;
        Node<T> newNode = new Node<>(previous, value, current);

        current.prev = newNode;

        if (previous == null) {
            first = newNode;
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

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> current = findNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;

        while (current != null) {
            if ((current.value == null && object == null)
                    || (current.value != null && current.value.equals(object))) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index + ", size: " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> current = first;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

            return current;
        }

        Node<T> current = last;

        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }

        return current;
    }

    private T unlink(Node<T> node) {
        final T removedValue = node.value;
        Node<T> previous = node.prev;
        Node<T> next = node.next;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }

        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
        }

        node.prev = null;
        node.next = null;

        size--;
        return removedValue;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
