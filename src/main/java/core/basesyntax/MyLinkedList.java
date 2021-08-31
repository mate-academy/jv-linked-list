package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String OUT_OF_BOUNDS_MESSAGE = "Index out of bounds: ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkByIndex(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> setterNode = getNode(index);
        T oldValue = setterNode.item;
        setterNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = head; i != null; i = i.next) {
            if ((object != null && object.equals(i.item) || i.item == object)) {
                unlink(i);
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

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    void linkByIndex(T value, Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (previousNode == null) {
            head = newNode;
        } else {
            previousNode.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    private void checkIndexForAdd(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSAGE + index);
        }
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSAGE + index);
        }
    }

    Node<T> getNode(int index) {
        if (index < (size / 2)) {
            Node<T> searchedNode = head;
            for (int i = 0; i < index; i++) {
                searchedNode = searchedNode.next;
            }
            return searchedNode;
        } else {
            Node<T> searchedNode = tail;
            for (int i = size - 1; i > index; i--) {
                searchedNode = searchedNode.prev;
            }
            return searchedNode;
        }
    }

    T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.prev;
        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.next = nextNode;
            node.prev = null;
        }
        if (node.next == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
            node.next = null;
        }
        size--;
        return node.item;
    }
}
