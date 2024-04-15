package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        final Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(oldTail, value, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndexForAdd(index);

        if (index == size) {
            add(value);
        } else {
            Node<T> successor = node(index);
            Node<T> predecessor = successor.prev;
            Node<T> newNode = new Node<>(predecessor, value, successor);
            successor.prev = newNode;
            if (predecessor == null) {
                head = newNode;
            } else {
                predecessor.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndexForAccess(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndexForAccess(index);
        Node<T> current = node(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndexForAccess(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> element = head; element != null; element = element.next) {
            if (object == element.value
                    || (object != null
                    && object.equals(element.value))) {
                unlink(element);
                return true;
            }
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

    private Node<T> node(int index) {
        Node<T> element;
        if (index < (size >> 1)) {
            element = head;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
        } else {
            element = tail;
            for (int i = size - 1; i > index; i--) {
                element = element.prev;
            }
        }
        return element;
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return element;
    }

    private void checkPositionIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Add operation out of bounds. Index: "
                            + index + ", Size: " + size);
        }
    }

    private void checkPositionIndexForAccess(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Access operation out of bounds. Index: "
                            + index + ", Size: " + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
