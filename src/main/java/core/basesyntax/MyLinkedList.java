package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            setFirstElement(newNode);
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }

        size ++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index, false);

        if (index == size) {
            add(value);

            return;
        }

        Node<T> newNode = new Node<>(value);

        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = getNodeAtIndex(index);

            newNode.prev = current.prev;
            newNode.next = current;

            if (current.prev != null) {
                current.prev.next = newNode;
            }

            current.prev = newNode;
        }

        size ++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index, true);

        Node<T> node = getNodeAtIndex(index);

        return node != null ? node.element : null;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, true);

        Node<T> node = getNodeAtIndex(index);

        T prevValue = node.element;

        node.element = value;

        return prevValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, true);

        Node<T> node = getNodeAtIndex(index);

        return removeNode(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (object == null && current.element == null
                    || object != null && object.equals(current.element)) {
                removeNode(current);

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

    private void validateIndex(int index, boolean isSizeIndexForbidden) {
        if (index < 0
                || index > size
                || (isSizeIndexForbidden && index == size)
        ) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void setFirstElement(Node<T> node) {
        head = node;
        tail = node;
    }

    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = (index < size / 2) ? head : tail;

        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T removeNode(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size --;

        return node.element;
    }

    private class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(E element) {
            this.element = element;
        }
    }
}
