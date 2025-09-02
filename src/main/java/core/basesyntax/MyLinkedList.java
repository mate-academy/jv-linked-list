package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = getNode(index);

        Node<T> node = new Node<>(current.prev, value, current);

        if (index == 0) {
            current.prev = node;
            head = node;
        } else {
            current.prev.next = node;
            current.prev = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list.isEmpty()) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> current = getNode(index);

        T oldValue = current.element;
        current.element = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> current = getNode(index);

        unlink(current);

        size--;
        return current.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (object != null && object.equals(current.element)
                    || (object == null && current.element == null)) {

                unlink(current);

                size--;
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

    private Node<T> getNode(int index) {
        Node<T> current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    private void unlink(Node<T> current) {
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
    }

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

}

