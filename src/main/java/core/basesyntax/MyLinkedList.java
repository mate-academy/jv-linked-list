package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
            size++;
            return;
        }

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

        Node<T> prev = current.prev;
        Node<T> newNode = new Node<>(prev, value, current);

        prev.next = newNode;
        current.prev = newNode;

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
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index must be in range "
                    + "of entered values of list!");
        }

        Node<T> current = findNode(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index must be in range "
                    + "of entered values of list!");
        }

        Node<T> current = findNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index must be in range "
                    + "of entered values of list!");
        }

        Node<T> current = findNode(index);
        T removed = current.value;
        unlink(current);

        return removed;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (object == null) {
                if (current.value == null) {
                    unlink(current);
                    return true;
                }
            } else {
                if (object.equals(current.value)) {
                    unlink(current);
                    return true;
                }
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

    private void unlink(Node<T> node) {
        if (node != null) {
            final Node<T> previous = node.prev;
            final Node<T> next = node.next;

            if (previous == null) {
                head = next;
            } else {
                previous.next = next;
                node.prev = null;
            }

            if (next == null) {
                tail = previous;
            } else {
                next.prev = previous;
                node.next = null;
            }
            node.value = null;
            size--;
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private Node<T> findNodeFromTail(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index must be in range "
                    + "of entered values of list!");
        }
        Node<T> current = tail;

        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private Node<T> findNodeFromHead(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index must be in range"
                    + " of entered values of list!");
        }
        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> findNode(int index) {
        if (index < size / 2) {
            return findNodeFromHead(index);
        }
        return findNodeFromTail(index);
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
