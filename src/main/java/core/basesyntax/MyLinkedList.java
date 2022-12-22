package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int INITIAL_LIST_SIZE = 10;
    private static final int FIRST_INDEX = 1;
    private int size;
    private T[] elementData;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    MyLinkedList() {
        first = new Node<T>(null, null, null);
        last = new Node<T>(null, null,null);
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(last, value, null);
        if (size == 0) {
            first = newNode;
            first.prev = null;
        }
        last.next = newNode;
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> currentNode = findNode(index);
        Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);
        if (index == 0) {
            first = newNode;
            newNode.next.prev = newNode;
            size++;
            return;
        }
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            Node<T> newNode = new Node<T>(last, value, null);
            last.next = newNode;
            last = newNode;
            size++;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNode(index);
        T returnValue = currentNode.item;
        currentNode.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = findNode(index);
        if (size == 1) {
            first = new Node<T>(null, null, null);
            last = new Node<T>(null, null, null);
        } else if (currentNode.prev == null) {
            currentNode.next.prev = null;
            first = currentNode.next;
        } else if (currentNode.next == null) {
            currentNode.prev.next = null;
            last =currentNode.prev;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item || currentNode.item != null
                    && currentNode.item.equals(object)) {
                return remove(i) == currentNode.item;
                /*if (size == 1) {
                    first = new Node<T>(null, null, null);
                    last = new Node<T>(null, null,null);
                } else if (currentNode.prev == null) {
                    currentNode.next.prev = currentNode.prev;
                } else {
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                }
                size--;
                return true;*/
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorect index: " + index);
        }
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
}


