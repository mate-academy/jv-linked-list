package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            addFirstElement(node);
            return;
        }
        tail.next = node;
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> oldNode = getIndexNode(index);
        if (size == 0) {
            addFirstElement(newNode);
            return;
        }
        if (index == size || index == 0) {
            addTopBottom(newNode, index);
            return;
        }
        newNode.prev = oldNode.prev;
        newNode.next = oldNode;
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexGetSet(index);
        Node<T> node = getIndexNode(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexGetSet(index);
        Node<T> node = getIndexNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexGetSet(index);
        Node<T> node = getIndexNode(index);
        T removedItem = node.item;
        if (index == 0) {
            removeBottom();
            return removedItem;
        }
        if (index == size - 1) {
            removeTop();
            return removedItem;
        }
        removeNode(node);
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node == null) {
            return false;
        }
        if (node.prev == null) {
            removeByObject();
            return true;
        }
        if (node.next == null) {
            removeTop();
            return true;
        }
        removeNode(node);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void checkIndexGetSet(int index) {
        if (index + 1 > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void addFirstElement(Node<T> newNode) {
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        }
    }

    private Node<T> getIndexNode(int index) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private Node<T> getNode(T element) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == element || node.item != null && node.item.equals(element)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    public void addTopBottom(Node<T> node, int index) {
        if (index > 0) {
            tail.next = node;
            node.prev = tail;
            tail = node;
            size++;
            return;
        }
        head.prev = node;
        node.next = head;
        head = node;
        size++;
    }

    private void removeNode(Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        size--;
    }

    public void removeBottom() {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        head.next.prev = null;
        head = head.next;
        size--;
    }

    private void removeByObject() {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        head.next.prev = null;
        head = head.next;
        size--;
    }

    private void removeTop() {
        tail.prev.next = null;
        tail = tail.prev;
        size--;
    }
}
