package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> perv, T element, Node<T> next) {
            this.prev = perv;
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
            linkBefore(value, getNode(index));
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
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        T oldValue = getNode(index).element;
        getNode(index).element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if (object == null && x.element == null) {
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
        Node<T> first = head;
        Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void linkBefore(T value, Node<T> transfer) {
        Node<T> last = transfer.prev;
        Node<T> newNode = new Node<>(last, value, transfer);
        transfer.prev = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> x) {
        final T deletedValue = x.element;
        Node<T> prev = x.prev;
        Node<T> next = x.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.element = null;
        size--;
        return deletedValue;
    }

    Node<T> getNode(int index) {
        Node<T> valueOfIndex = head;
        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                valueOfIndex = valueOfIndex.next;
            }
        } else {
            valueOfIndex = tail;
            for (int i = size - 1; i > index; i--) {
                valueOfIndex = valueOfIndex.prev;
            }
        }
        return valueOfIndex;
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
