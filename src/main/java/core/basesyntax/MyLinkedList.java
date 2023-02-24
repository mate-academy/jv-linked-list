package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newValue = new Node<>(last, value, null);
        if (isEmpty()) {
            first = newValue;
        } else {
            last.next = newValue;
            newValue.prev = last;
        }
        last = newValue;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        Node<T> newValue = new Node<>(null, value, null);
        if (index == 0) {
            newValue.next = first;
            first.prev = newValue;
            first = newValue;
        } else {
            checkIndex(index);
            Node<T> current = findNodeByIndex(index);
            newValue.prev = current.prev;
            current.prev.next = newValue;
            newValue.next = current;
            current.prev = newValue;
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
        Node<T> current = findNodeByIndex(index);
        return current.nodeValue;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = findNodeByIndex(index);
        T removedValue = current.nodeValue;
        current.nodeValue = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNodeByIndex(index);
        unlink(current);
        return current.nodeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.nodeValue == object || current.nodeValue != null
                    && current.nodeValue.equals(object)) {
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

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private void unlink(Node node) {
        if (node.prev == null) {
            first = first.next;
        } else if (node.next == null) {
            last = node.prev;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if ((size / 2) > index) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size; i > (index + 1); i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<T> {
        private T nodeValue;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T nodeValue, Node<T> next) {
            this.nodeValue = nodeValue;
            this.prev = prev;
            this.next = next;
        }
    }
}
