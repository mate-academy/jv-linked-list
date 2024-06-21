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
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = node;
            tail = head;
            size++;
        } else {
            node = new Node<>(tail, value, null);
            node.prev.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexRange(index);
        if (index < size / 2) {

        } else {

        }
    }

    @Override
    public void addAll(List<T> list) {

    }

    @Override
    public T get(int index) {
        checkIndexRange(index);
        T foundElement;
        if (index < size / 2) {
            foundElement = searchFromHead(index);
        } else {
            foundElement = searchFromTail(index);
        }
        return foundElement;
    }

    @Override
    public T set(T value, int index) {
        checkIndexRange(index);
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndexRange(index);
        return null;
    }

    @Override
    public boolean remove(T object) {
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

    private void checkIndexRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                                                        + index
                                                        + " out of bounds "
                                                        + "for size "
                                                        + size);
        }
    }

    private T searchFromHead(int index) {
        T foundElementByIndexFromHead = null;
        if (index == 0) {
            return head.element;
        } else {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                foundElementByIndexFromHead = currentNode.element;
            }
        }
        return foundElementByIndexFromHead;
    }

    private T searchFromTail(int index) {
        T foundElementByIndexFromTail = null;
        if (index == size - 1) {
            return tail.element;
        } else {
            Node<T> currentNode = tail;
            for (int i = 0; i < index; i++) {
                foundElementByIndexFromTail = currentNode.element;
            }
        }
        return foundElementByIndexFromTail;
    }

    private class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
