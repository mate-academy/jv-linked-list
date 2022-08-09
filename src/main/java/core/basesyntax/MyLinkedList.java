package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private static final int SIZE_EMPTY_LIST = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size() == SIZE_EMPTY_LIST) {
            head = newNode;
        }
        if (size > SIZE_EMPTY_LIST) {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < SIZE_EMPTY_LIST) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range.");
        }
        if (index == SIZE_EMPTY_LIST) {
            createHead(value);
        } else if (index == size()) {
            createTail(value);
        } else {
            createItem(value, index);
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
        checkIndexOutOfBoundsException(index);
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOutOfBoundsException(index);
        T oldValue = findByIndex(index).item;
        findByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfBoundsException(index);
        Node<T> currentNode = findByIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = findByValue(object);
        if (currentNode == null) {
            return false;
        }
        unlink(currentNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == SIZE_EMPTY_LIST;
    }

    private Node<T> findByIndex(int index) {
        return size() / 2 > index
                ? searchingDown(index)
                : searchingUp(index);
    }

    private Node<T> searchingDown(int index) {
        Node<T> currentNode = head;
        for (int i = 1; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> searchingUp(int index) {
        Node<T> currentNode = tail;
        for (int i = size() - 2; i >= index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private Node<T> findByValue(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (equals(currentNode.item, value)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }

    private void checkIndexOutOfBoundsException(int index) {
        if (index >= size() || index < SIZE_EMPTY_LIST) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range.");
        }
    }

    private boolean equals(Object first, Object second) {
        return first == second || (first != null && first.equals(second));
    }

    private void createHead(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size() == SIZE_EMPTY_LIST) {
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
        }
        head = newNode;
        size++;
    }

    private void createTail(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private void createItem(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> currentNode = findByIndex(index);
        newNode.prev = currentNode.prev;
        newNode.next = currentNode;
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }
}
