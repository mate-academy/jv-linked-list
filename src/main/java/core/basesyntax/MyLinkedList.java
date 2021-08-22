package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node(null, value, null);
            last = first;
            size++;
        } else if (size > 0) {
            last = new Node(getNodeByIndex(size - 1), value, null);
            getNodeByIndex(size - 1).next = last;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index);
        if (index == size) {
            add(value);
        }
        if (index == 0) {
            first = new Node(null, value, first);
            getNodeByIndex(1).prev = first;
            size++;
        }
        Node<T> newNode = new Node(getNodeByIndex(index).prev, value, getNodeByIndex(index - 1).next);
        // try after test change getNodeByIndex(index - 1).next -->  (Node) getNodeByIndex(index).item
        getNodeByIndex(index - 1).next = newNode;
        getNodeByIndex(index + 1).prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
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
        return false;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> bufferNode = first;
        for (int i = 0; i < index; i++) {
            bufferNode = bufferNode.next;
        }
        return bufferNode;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void indexCheck(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("This index does not exist");
        }
    }
}
