package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
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
        Node<T> nodeAtIndex = findNode(index);
        Node<T> oldHead = head;
        if (nodeAtIndex.previous == null) {
            head = new Node<>(null, value, oldHead);
        } else {
            Node<T> newNode = new Node<>(nodeAtIndex.previous, value, nodeAtIndex);
            newNode.previous.next = newNode;
            nodeAtIndex.previous = newNode;
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
        indexValidation(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        Node<T> nodeToChange = findNode(index);
        T oldItem = nodeToChange.item;
        nodeToChange.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = findNode(index);
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return nodeToRemove.item;
        }
        if (nodeToRemove == head) {
            head = nodeToRemove.next;
            head.previous = null;
            nodeToRemove.next = null;
            size--;
            return nodeToRemove.item;
        }
        if (nodeToRemove == tail) {
            tail = nodeToRemove.previous;
            tail.next = null;
            nodeToRemove.previous = null;
            size--;
            return nodeToRemove.item;
        }
        nodeToRemove.next.previous = nodeToRemove.previous;
        nodeToRemove.previous.next = nodeToRemove.next;
        size--;
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> nodeToFind = head;
        for (int i = 0; i < size; i++) {
            if (nodeToFind.item == null ? t == nodeToFind.item : nodeToFind.item.equals(t)) {
                remove(i);
                return true;
            }
            nodeToFind = head.next;
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

    private Node<T> findNode(int index) {
        indexValidation(index);
        Node<T> nodeToFind;
        if (index < (size >> 1)) {
            nodeToFind = head;
            for (int i = 0; i < index; i++) {
                nodeToFind = nodeToFind.next;
            }
        } else {
            nodeToFind = tail;
            for (int i = size - 1; i > index; i--) {
                nodeToFind = nodeToFind.previous;
            }
        }
        return nodeToFind;
    }

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not valid index.");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        private Node(Node<T> previous, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }
}
