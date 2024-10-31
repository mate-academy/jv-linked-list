package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = findNode(index);
        Node<T> newNode = new Node<>(current.previous, value, current);

        if (current.previous == null) {
            first = newNode;
        } else {
            current.previous.next = newNode;
        }
        current.previous = newNode;
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
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = findNode(index);
        T oldValue = element.value;
        element.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = unlink(findNode(index));
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.value == object
                    || current.value != null && current.value.equals(object)) {
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

    private Node<T> findNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (size / 2 > index) {
            Node<T> element = first;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
            return element;
        } else {
            Node<T> element = last;
            for (int i = size - 1; i > index; i--) {
                element = element.previous;
            }
            return element;
        }
    }

    private Node<T> unlink(Node<T> node) {
        if (node.previous == null) {
            first = node.next;
            if (first != null) {
                first.previous = null;
            }
        } else {
            node.previous.next = node.next;
        }

        if (node.next == null) {
            last = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
        return node;
    }

    private class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}

