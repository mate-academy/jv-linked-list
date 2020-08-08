package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstListElement;
    private Node<T> lastListElement;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(lastListElement, value, null);
        if (size == 0) {
            firstListElement = node;
        }
        if (size > 0) {
            lastListElement.next = node;
        }
        lastListElement = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkNodeInList(index);
        Node<T> oldNode = getNodeFromList(index);
        Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
        if (index > 0) {
            newNode.prev.next = newNode;
        } else {
            firstListElement = newNode;
        }
        oldNode.prev = newNode;
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
        checkNodeInList(index);
        Node<T> node = getNodeFromList(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkNodeInList(index);
        Node<T> node = getNodeFromList(index);
        T oldNodeValue = node.item;
        node.item = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        checkNodeInList(index);
        Node<T> node = getNodeFromList(index);
        if (index > 0) {
            node.prev.next = node.next;
        } else {
            firstListElement = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            lastListElement = node.prev;
        }
        T removedItem = node.item;
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = firstListElement;
        for (int i = 0; i < size; i++) {
            if (node.item == t || node.item.equals(t)) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private boolean checkNodeInList(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return true;
    }

    private Node<T> getNodeFromList(int index) {
        Node<T> node = firstListElement;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
