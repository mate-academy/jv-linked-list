package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> firstNode;
    private Node<T> lastNode;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(T element, Node<T> prev, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void addToEmpty(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        firstNode = newNode;
        lastNode = newNode;
        size++;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(value,null, firstNode);
        firstNode.prev = newNode;
        firstNode = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Item with index  " + index + " does not exist.");
        }
        Node<T> searchedNode;
        if (index < size / 2) {
            searchedNode = firstNode;
            for (int i = 0; i < index; i++) {
                searchedNode = searchedNode.next;
            }
        } else {
            searchedNode = lastNode;
            for (int i = size - 1; i > index; i--) {
                searchedNode = searchedNode.prev;
            }
        }
        return searchedNode;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node<T> newNode = new Node<>(value, lastNode, null);
            lastNode.next = newNode;
            lastNode = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " is an invalid index value");
        }
        if (size == 0) {
            addToEmpty(value);
        } else if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            add(value);
        } else {
            Node<T> prevNode = getNode(index - 1);
            Node<T> newNode = new Node<>(value, prevNode, prevNode.next);
            prevNode.next.prev = newNode;
            prevNode.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " is an invalid index value");
        }
        getNode(index).item = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Item with index  " + index + " does not exist.");
        }
        Node<T> removedNode = getNode(index);
        if (removedNode.prev == null) {
            firstNode = removedNode.next;
        } else {
            removedNode.prev.next = removedNode.next;
        }
        if (removedNode.next == null) {
            lastNode = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
        }
        size--;
        T resultToRemove = removedNode.item;
        removedNode = null;
        return resultToRemove;
    }

    @Override
    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            if (get(i) == t) {
                return remove(i);
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
