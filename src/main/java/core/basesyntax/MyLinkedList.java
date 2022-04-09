package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;
    private int size = 0;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            if (size == 1) {
                head.next = tail;
            }
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (currentNode.prev == null) {
                head = newNode;
            } else {
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        getNode(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (index == size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        getNode(index);
        T beforeRemove = current.value;
        current.value = value;
        return beforeRemove;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> remove = getNode(index);
        if (size == 1) {
            tail = head = null;
            size--;
        } else {
            removedLogic(remove.value);
        }
        return remove.value;
    }

    @Override
    public boolean remove(T object) {
        return removedLogic(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        int currentIndex = 0;
        current = head;
        while (current != null) {
            if (currentIndex == index) {
                return current;
            }
            current = current.next;
            currentIndex++;
        }
        return null;
    }

    private boolean removedLogic(T value) {
        current = head;
        while (current != null) {
            if (current.value == value || current.value != null && current.value.equals(value)) {
                if (size == 1) {
                    tail = head = null;
                } else if (current.prev == null) {
                    current.next.prev = null;
                    current = current.next;
                    head = current;
                } else {
                    Node<T> prevNode = current.prev;
                    Node<T> nextNode = current.next;
                    prevNode.next = nextNode;
                    nextNode.prev = prevNode;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
