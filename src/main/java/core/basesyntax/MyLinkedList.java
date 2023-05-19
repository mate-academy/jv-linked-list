package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkingCapacity(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
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
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = getNode(index);
        T oldValue = oldNode.element;
        oldNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (x.element == object) {
                unlink(x);
                return true;
            }
            if (object != null && object.equals(x.element)) {
                unlink(x);
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

    private void linkFirst(T value) {
        Node<T> head = this.head;
        Node<T> newNode = new Node<>(null, value, head);
        this.head = newNode;
        if (head == null) {
            tail = newNode;
        } else {
            head.prev = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> tail = this.tail;
        Node<T> newNode = new Node<>(tail, value, null);
        this.tail = newNode;
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> newNode = new Node<>(prev, value, node);
        node.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        int i;
        checkPositionIndex(index);
        if (index < (size >> 1)) {
            node = head;
            for (i = 0;i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> unlinkElement) {
        final T element = unlinkElement.element;
        final Node<T> next = unlinkElement.next;
        final Node<T> prev = unlinkElement.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            unlinkElement.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            unlinkElement.next = null;
        }
        unlinkElement.element = null;
        size--;
        return element;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Element doesn't exist at " + index);
        }
    }

    private void checkingCapacity(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Element doesn't exist at " + index);
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
