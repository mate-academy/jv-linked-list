package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            first = new Node<>(value, null, null);
            size++;
            return true;
        }
        if (size == 1) {
            last = new Node<>(value, null, first);
            first.next = last;
            size++;
            return true;
        }
        last.next = new Node<>(value, null, last);
        last = last.next;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This is index either negative or bigger than the actual size of list");
        }

    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        isIndexSuits(index);
        int currentSize = 0;
        Node<T> currentNode = first;
        while (currentSize < index) {
            currentNode = currentNode.next;
            currentSize++;
        }
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        isIndexSuits(index);
        int currentSize = 0;
        Node<T> currentNode = first;
        while(currentSize < index) {
            currentNode = currentNode.next;
            currentSize++;
        }
        T returnElement = currentNode.element;
        currentNode.element = value;
        return returnElement;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T t) {
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

    private void isIndexSuits(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This is index either negative or bigger than the actual size of list");
        }
    }

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.element = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
