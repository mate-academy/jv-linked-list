package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> tempNode = searchNode(index);
        Node<T> newPrev = tempNode.prev;
        Node<T> newNode = new Node<>(newPrev, value, tempNode);
        tempNode.prev = newNode;
        if (newPrev == null) {
            head = newNode;
        } else {
            newPrev.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return searchNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = searchNode(index);
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        return unlink(searchNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempNode = head;
        while (tempNode != null) {
            if (tempNode.item == object || tempNode.item != null && tempNode.item.equals(object)) {
                unlink(tempNode);
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
        return size == 0;
    }

    private Node<T> searchNode(int index) {
        checkIndex(index);
        Node<T> tempNode = head;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
            return tempNode;
        } else {
            tempNode = tail;
            for (int i = 1; i < size - index; i++) {
                tempNode = tempNode.prev;
            }
        }
        return tempNode;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        Node<T> prev = node.prev;
        Node<T> next = node.next;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }
}
