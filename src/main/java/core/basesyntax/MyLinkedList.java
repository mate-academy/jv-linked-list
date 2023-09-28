package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        if (size++ == 0) {
            tail = new Node<>(null, value, null);
            head = tail;
            return;
        }
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> indexedElement = getIndexedElement(index);
        Node<T> newElement = new Node<>(indexedElement.prev, value, indexedElement);
        if (index == 0) {
            indexedElement.prev = newElement;
            head = newElement;
            size++;
            return;
        }
        newElement.prev.next = newElement;
        indexedElement.prev = newElement;
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
        return getIndexedElement(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldItem = getIndexedElement(index).item;
        getIndexedElement(index).item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> deletedNode = getIndexedElement(index);
        unlink(deletedNode);
        return deletedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedNode;
        for (int i = 0; i < size; i++) {
            deletedNode = getIndexedElement(i);
            if ((deletedNode.item != null && deletedNode.item.equals(object))
                    || (deletedNode.item == null && object == null)) {
                unlink(deletedNode);
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

    private Node<T> getIndexedElement(int index) {
        if ((index >= size || index < 0) && index != 0) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn`t exist."
                    + " Actual size is " + size);
        }
        Node<T> indexedElement;
        if (index < size / 2) {
            indexedElement = head;
            for (int i = 0; i < index; i++) {
                indexedElement = indexedElement.next;
            }
        } else {
            indexedElement = tail;
            for (int i = 0; i < size - index - 1; i++) {
                indexedElement = indexedElement.prev;
            }
        }
        return indexedElement;
    }

    private void unlink(Node<T> node) {
        if (size-- == 1) {
            head = null;
            tail = null;
            return;
        }
        if (node == head) {
            node.next.prev = null;
            head = node.next;
            return;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }
}
