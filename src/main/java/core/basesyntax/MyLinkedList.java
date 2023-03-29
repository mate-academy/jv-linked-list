package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);

        if (first == null) {
            first = node;
        } else if (last == null) {
            last = node;
            last.prev = first;
            first.next = last;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        checkIfIndexExists(index);

        Node<T> node = new Node<>(value);
        Node<T> current = getNodeByIndex(index);
        Node<T> previous = current.prev;

        if (previous != null) {
            previous.next = node;
        } else {
            first = node;
        }

        current.prev = node;
        node.prev = previous;
        node.next = current;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeByIndex(index);

        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        Node<T> previous = node.prev;
        Node<T> next = node.next;

        if (previous != null) {
            previous.next = next;
        } else {
            first = next;
        }

        if (next != null) {
            next.prev = previous;
        } else {
            last = previous;
        }

        size--;

        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;

        for (int i = 0; i < size(); i++) {
            boolean areValuesEqual = node.value == null
                    ? object == null
                    : node.value.equals(object);

            if (areValuesEqual) {
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

    private void checkIfIndexExists(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIfIndexExists(index);
        Node<T> node = first;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
