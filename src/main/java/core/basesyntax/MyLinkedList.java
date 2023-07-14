package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

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
            linkMiddle(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexExist(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexExist(index);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexExist(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
                unlink(current);
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

    private T unlink(Node<T> node) {
        final T element = node.item;
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }

    private void linkMiddle(T element, Node<T> currentIndexNode) {
        Node<T> prevNode = currentIndexNode.prev;
        Node<T> newNode = new Node<>(prevNode, element, currentIndexNode);
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        currentIndexNode.prev = newNode;
        size++;
    }

    private void linkLast(T element) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<>(prevNode, element, null);
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " is out of bounds of size: " + size);
        }
    }

    private void checkIndexExist(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " is non existing index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        if (index < (size / 2)) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || this.item.getClass() != obj.getClass()) {
                return false;
            }
            Node<T> current = (Node<T>) obj;
            return (this.item != null && this.item.equals(current.item));
        }
    }
}
