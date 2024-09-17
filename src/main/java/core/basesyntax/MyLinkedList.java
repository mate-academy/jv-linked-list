package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (this.isEmpty()) {
            this.setFirstElement(newNode);
        } else {
            newNode.prev = tail;
            this.tail.next = newNode;
            this.tail = newNode;
        }

        this.size += 1;
    }

    @Override
    public void add(T value, int index) {
        this.validateIndex(index, false);

        if (index == this.size) {
            this.add(value);

            return;
        }

        Node<T> newNode = new Node<>(value);

        if (index == 0) {
            newNode.next = head;
            this.head.prev = newNode;
            this.head = newNode;
        } else {
            Node<T> current = this.getNodeAtIndex(index);

            newNode.prev = current.prev;
            newNode.next = current;

            if (current.prev != null) {
                current.prev.next = newNode;
            }

            current.prev = newNode;
        }

        this.size += 1;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            this.add(element);
        }
    }

    @Override
    public T get(int index) {
        this.validateIndex(index, true);

        Node<T> node = this.getNodeAtIndex(index);

        return node != null ? node.element : null;
    }

    @Override
    public T set(T value, int index) {
        this.validateIndex(index, true);

        Node<T> node = this.getNodeAtIndex(index);

        T prevValue = node.element;

        node.element = value;

        return prevValue;
    }

    @Override
    public T remove(int index) {
        this.validateIndex(index, true);

        Node<T> node = this.getNodeAtIndex(index);

        return this.removeNode(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = this.head;

        while (current != null) {
            if ((object == null && current.element == null) ||
                    (object != null && object.equals(current.element))) {
                this.removeNode(current);

                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private void validateIndex(int index, boolean isSizeIndexForbidden) {
        if (index < 0
                || index > this.size
                || (isSizeIndexForbidden && index == this.size)
        ) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
    }

    private void setFirstElement(Node<T> node) {
        this.head = node;
        this.tail = node;
    }

    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = (index < this.size / 2) ? this.head : this.tail;

        if (index < this.size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            for (int i = this.size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T removeNode(Node<T> node) {
        if (node.prev == null) {
            this.head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            this.tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size -= 1;

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
