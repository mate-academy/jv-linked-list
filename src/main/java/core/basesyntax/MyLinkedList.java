package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value);
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        if (head == null) {
            addFirst(value);
        } else if (size == index) {
            addLast(value);
        } else {
            Node<T> currentIndexNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentIndexNode.prev, value, currentIndexNode);
            if (currentIndexNode.next != null) {
                currentIndexNode.prev.next = newNode;
            } //TODO!

            currentIndexNode.prev = newNode;
        }
        size++;
    }

    private void addFirst(T value) {

        Node<T> NodeToAdd = new Node<>(null, value, null);
        head = NodeToAdd;
        tail = NodeToAdd;
    }

    private void addLast(T value) {
        Node<T> NodeToAdd = new Node<>(tail, value, null);
        tail.next = NodeToAdd;
        tail = NodeToAdd;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

//    TODO!
    @Override
    public T remove(int index) {
        size--;
        return null;
    }

    @Override
    public boolean remove(T object) {
        size--;
        return false;
    }

    // TODO!
    private void unlink(Node<T> node) {
        return;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
