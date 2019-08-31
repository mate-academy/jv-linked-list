package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private T data;
        private Node<T> previous;
        private Node<T> next;

        public Node(T data, Node<T> previous, Node<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }

    private int size = 0;
    private Node<T> firstOne;
    private Node<T> lastOne;


    public Node<T> getElem(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> nodeIndex = firstOne;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                nodeIndex = nodeIndex.next;
            }
        } else {
            nodeIndex = lastOne;
            for (int i = size - 1; i > index; i--) {
                nodeIndex = nodeIndex.previous;
            }
        }
        return nodeIndex;
    }

    @Override
    public void add(T value) {
        Node<T> lastNode = lastOne;
        Node<T> newNode = new Node<>(value, lastNode, null);
        lastOne = newNode;
        if (lastNode == null) {
            firstOne = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is out of range: " + index);
        }
        if (index == size) {
            add(value);
        } else {
            addFirst(value);
        }
    }

    private void addFirst(T value) {
        Node<T> firstNode = firstOne;
        Node<T> newNode = new Node<>(value, null, firstNode);
        firstOne = newNode;
        if (firstNode == null) {
            lastOne = newNode;
        } else {
            firstNode.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            this.add(t);
        }
    }

    @Override
    public T get(int index) {
        return getElem(index).data;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        getElem(index).data = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> removeElem = getElem(index);
        Node<T> next = removeElem.next;
        Node<T> previous = removeElem.previous;
        if (previous == null) {
            firstOne = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            lastOne = previous;
        } else {
            next.previous = previous;
        }
        T removedData = removeElem.data;
        removeElem.data = null;
        size--;
        return removedData;
    }

    @Override
    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getElem(i).data.equals(t)) {
                T deletedItem = getElem(i).data;
                remove(i);
                return deletedItem;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}