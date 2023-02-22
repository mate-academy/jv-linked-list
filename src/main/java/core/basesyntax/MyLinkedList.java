package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> next = searchByIndex(index);
        Node<T> previous = next.previous;
        Node<T> current = new Node<>(previous, value, next);
        next.previous = current;
        if (previous == null) {
            head = current;
        } else {
            previous.next = current;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return searchByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = searchByIndex(index);
        T oldValue = current.element;
        current.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(searchByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; current != null; i++) {
            if (current.element == object || current.element != null
                    && current.element.equals(object)) {
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

    private static class Node<T> {
        private Node<T> previous;
        private T element;
        private Node<T> next;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " low than 0 or bigger than array size" + size);
        }
    }

    private Node<T> searchByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i != index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i != index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private T unlink(Node<T> node) {
        T element = node.element;
        Node<T> previous = node.previous;
        Node<T> next = node.next;
        if (previous == null) {
            head = next;
        } else {

            previous.next = next;
            node.previous = null;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            node.next = null;
        }
        node.element = null;
        size--;
        return element;
    }
}
