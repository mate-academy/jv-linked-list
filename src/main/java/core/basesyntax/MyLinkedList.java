package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public boolean add(T data) {
        insertLast(data);
        return true;
    }

    @Override
    public void add(T data, int index) {
        checkBounds(index);
        if (index == size) {
            insertLast(data);
        } else {
            insertBefore(data, getNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            insertLast(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return removeNode(getNode(index));
    }

    @Override
    public boolean remove(T object) {

        for (Node<T> first = head; first != null; first = first.next) {
            if (first.data == null) {
                removeNode(first);
                return true;
            }
            if (object != null && object.equals(first.data)) {
                removeNode(first);
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

    private void insertLast(T data) {
        Node<T> newNode = new Node<T>(tail, data, null);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    public void insertBefore(T element, Node<T> follow) {
        Node<T> prior = follow.previous;
        Node<T> newNode = new Node<>(prior, element, follow);
        follow.previous = newNode;
        if (prior == null) {
            head = newNode;
        } else {
            prior.next = newNode;
        }
        size++;
    }

    private T removeNode(Node<T> removed) {
        final T element = removed.data;
        Node<T> next = removed.next;
        Node<T> prev = removed.previous;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            removed.previous = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
            removed.next = null;
        }
        removed.data = null;
        size--;
        return element;
    }

    private Node<T> getNode(int index) {
        if (index < (size / 2)) {
            Node<T> forward = head;
            for (int k = 0; k < index; k++) {
                forward = forward.next;
            }
            return forward;
        } else {
            Node<T> backward = tail;
            for (int k = size - 1; k > index; k--) {
                backward = backward.previous;
            }
            return backward;
        }
    }

    private void checkBounds(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "This index: " + index + " is not valid for add operation");
        }
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(
                    "This index: " + index + " not point at any element in this list");
        }
    }
}
