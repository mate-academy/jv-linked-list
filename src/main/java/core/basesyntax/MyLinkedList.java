package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ZERO = 0;
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (first == null) {
            node.next = null;
            node.prev = null;
            first = node;
        } else {
            last.next = node;
            node.prev = last;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " absent in this list");
        }
        Node<T> node = new Node<>(value);
        if (index == 0) {
            if (first == null) {
                node.next = null;
                node.prev = null;
                first = node;
                last = node;
            } else {
                first.prev = node;
                node.next = first;
                first = node;
                node.prev = null;
            }
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> tempNode = first;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        Node<T> previous = tempNode.prev;
        previous.next = node;
        tempNode.prev = node;
        node.prev = previous;
        node.next = tempNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        Node<T> tempNode = first;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        return tempNode.item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> node = new Node<>(value);
        T returnedValue;
        if (index == 0) {
            node.prev = null;
            node.next = first.next;
            returnedValue = first.item;
            first = node;
            return returnedValue;
        }
        Node<T> tempNode = first;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        returnedValue = tempNode.item;
        tempNode.item = node.item;
        return returnedValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        T removedItem;
        if (index == 0) {
            if (first.next == null) {
                removedItem = first.item;
                first = null;
                size = 0;
                return removedItem;
            }
            first.next.prev = null;
            removedItem = first.item;
            first = first.next;
            size--;
            return removedItem;
        }
        if (index == size - 1) {
            last.prev.next = null;
            removedItem = last.item;
            last = last.prev;
            size--;
            return removedItem;
        }
        Node<T> tempNode = first;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        removedItem = tempNode.item;
        tempNode.prev.next = tempNode.next;
        tempNode.next.prev = tempNode.prev;
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempNode = first;
        for (int i = 0; i < size; i++) {
            if (tempNode.item == object || (object != null && object.equals(tempNode.item))) {
                remove(i);
                return true;
            }
            tempNode = tempNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == ZERO;
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " absent in this list");
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public Node(E element) {
            this.item = element;
        }
    }
}
