package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirst(value);
        } else {
            addToEnd(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (size == 0 && index == 0) {
            addFirst(value);
        } else if (index == 0) {
            addToStart(value);
        } else if (index == size) {
            addToEnd(value);
        } else {
            insert(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return indexSearch(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = indexSearch(index).item;
        indexSearch(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> deleteNode = indexSearch(index);
        if (index == 0) {
            first = first.next;
        } else if (index > 0 & index < size - 1) {
            deleteNode.next.prev = deleteNode.prev;
            deleteNode.prev.next = deleteNode.next;
        } else {
            last = last.prev;
        }
        size--;
        return deleteNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            Node<T> temporaryNode = indexSearch(i);
            if (object == temporaryNode.item
                    || object != null && object.equals(temporaryNode.item)) {
                remove(i);
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

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        first = newNode;
        last = newNode;
        size++;
    }

    private void addToStart(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void insert(T value, int index) {
        Node<T> indexNode = indexSearch(index - 1);
        Node<T> insertNode = new Node<>(indexNode, value, indexNode.next);
        indexNode.next.prev = insertNode;
        indexNode.next = insertNode;
        size++;
    }

    private void addToEnd(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private Node<T> indexSearch(int index) {
        checkIndex(index);
        Node<T> currentNode = first;
        int indexOfCurrentElement = 0;
        while (indexOfCurrentElement < index) {
            currentNode = currentNode.next;
            indexOfCurrentElement++;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 & index < size)) {
            throw new IndexOutOfBoundsException("Index out of list size!!! Size = " + size);
        }
    }
}
