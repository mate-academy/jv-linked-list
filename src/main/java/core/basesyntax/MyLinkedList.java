package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("The list parameter cannot be null.");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> setNode = findNode(index);
        T oldValue = setNode.value;
        setNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removalNode = findNode(index);
        if (removalNode.prev == null) {
            head = removalNode.next;
        } else {
            removalNode.prev.next = removalNode.next;
        }
        if (removalNode.next == null) {
            tail = removalNode.prev;
        } else {
            removalNode.next.prev = removalNode.prev;
        }
        size--;
        return removalNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (isEqual(currentNode.value, object)) {
                if (currentNode.prev == null) {
                    head = currentNode.next;
                } else {
                    currentNode.prev.next = currentNode.next;
                }
                if (currentNode.next == null) {
                    tail = currentNode.prev;
                } else {
                    currentNode.next.prev = currentNode.prev;
                }
                size--;
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

    private boolean isEqual(T value, T obj) {
        return Objects.equals(obj, value);
    }

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> findNode;
        if (index < size / 2) {
            findNode = head;
            for (int i = 0; i < index; i++) {
                findNode = findNode.next;
            }
        } else {
            findNode = tail;
            for (int i = size - 1; i > index; i--) {
                findNode = findNode.prev;
            }
        }
        return findNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index "
                    + index
                    + " is out of bounds for size "
                    + size);
        }

    }

    private void checkAddIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index "
                    + index
                    + " is out of bounds for size "
                    + size);
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
