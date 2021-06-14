package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private T element;
        private Node<T> perv;
        private Node<T> next;

        public Node(Node<T> perv, T element, Node<T> next) {
            this.perv = perv;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T val : list) {
            add(val);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        T oldValue = node(index).element;
        node(index).element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.element == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.element)) {
                    unlink(x);
                    return true;
                }
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

    public void linkFirst(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.perv = newNode;
        }
        size++;
    }

    public void linkLast(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    public void linkBefore(T value, Node<T> transfer) {
        final Node<T> last = transfer.perv;
        final Node<T> newNode = new Node<>(last, value, transfer);
        transfer.perv = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    public T unlink(Node<T> x) {
        final T delValue = x.element;
        final Node<T> prev = x.perv;
        final Node<T> next = x.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.perv = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.perv = prev;
            x.next = null;
        }
        x.element = null;
        size--;
        return delValue;
    }

    Node<T> node(int index) {
        Node<T> x = head;
        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.perv;
            }
        }
        return x;
    }

    public void checkIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index is invalid: " + index);
        }
    }

    public void checkPositionIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index is invalid: " + index);
        }
    }

}
