package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(value, null, null);
            tail = head;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(value, tail, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nextNode = findNode(index);
        Node<T> insertedNode = new Node<>(value, nextNode.prev, nextNode);
        if (index == 0) {
            nextNode.prev = insertedNode;
            head = insertedNode;
        } else {
            nextNode.prev.next = insertedNode;
            nextNode.prev = insertedNode;
        }
        size++;
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
        checkElement(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        checkElement(index);
        Node<T> changedNode = findNode(index);
        T oldValue = changedNode.item;
        changedNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        checkElement(index);
        final Node<T> removed = findNode(index);
        size--;
        if (index == 0) {
            return removeHead();
        }
        if (index == size) {
            return removeTail();
        }
        return unlink(removed);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item || object != null && object.equals(currentNode.item)) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("You have passed incorrect index,"
                    + " index < 0 OR index > size");
        }
    }

    private void checkElement(int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("You have passed incorrect index,"
                    + "Node at index == size is null");
        }
    }

    private Node<T> findNode(int index) {
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        Node<T> searchedNode;
        if (index < (size / 2) + 1) {
            searchedNode = head;
            for (int i = 1; i < (size / 2) + 1; i++) {
                searchedNode = searchedNode.next;
                if (index == i) {
                    break;
                }
            }
        } else {
            searchedNode = tail;
            for (int i = size - 2; i > (size / 2) - 1; i--) {
                searchedNode = searchedNode.prev;
                if (index == i) {
                    break;
                }
            }
        }
        return searchedNode;
    }

    private T removeHead() {
        Node<T> removed = head;
        T value = removed.item;
        if (size == 0) {
            head = null;
            return value;
        }
        head = removed.next;
        head.prev = null;
        return value;
    }

    private T removeTail() {
        Node<T> removed = tail;
        T value = removed.item;
        tail = removed.prev;
        tail.next = null;
        return value;
    }

    private T unlink(Node<T> removed) {
        Node<T> previous = removed.prev;
        Node<T> next = removed.next;
        previous.next = next;
        next.prev = previous;
        return removed.item;
    }
}
