package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int EQUALS_ZERO = 0;
    private static final int EQUALS_ONE = 1;
    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;
    private int size = 0;

    @Override
    public void add(T value) {
        if (size == EQUALS_ZERO) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            if (size == EQUALS_ONE) {
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
            Node<T> currentNode = getIndex(index);
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
        current = head;
        int currentIndex = 0;
        while (current != null) {
            if (currentIndex == index) {
                return current.value;
            }
            current = current.next;
            currentIndex++;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (index == size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        int currentIndex = 0;
        current = head;
        while (current != null) {
            if (currentIndex == index) {
                T beforeRemove = current.value;
                current.value = value;
                return beforeRemove;
            }
            current = current.next;
            currentIndex++;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> rem = getIndex(index);
        if (size == 1) {
            tail = head = null;
            size--;
        } else {
            remove(rem.value);
        }
        return rem.value;
    }

    @Override
    public boolean remove(T object) {
        current = head;
        while (current != null) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == EQUALS_ZERO;
    }

    private Node<T> getIndex(int index) {
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

    private static class Node<T> {
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
