package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    public MyLinkedList() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            firstNode = newNode;
            lastNode = newNode;
        } else if (index == 0) {
            newNode.next = firstNode;
            firstNode.prev = newNode;
            firstNode = newNode;
        } else if (index == size) {
            newNode.prev = lastNode;
            lastNode.next = newNode;
            lastNode = newNode;
        } else {
            Node<T> current = firstNode;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = firstNode;
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (size == 1) {
            firstNode = null;
            lastNode = null;
        } else if (current == firstNode) {
            firstNode = current.next;
            if (firstNode != null) {
                firstNode.prev = null;
            }
        } else if (current == lastNode) {
            lastNode = current.prev;
            if (firstNode != null) {
                firstNode.prev = null;
            }
        } else {
            if (current.prev != null) {
                current.prev.next = current.next;
            }
            if (current.next != null) {
                current.next.prev = current.prev;
            }
        }
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        while (current != null) {
            if (current.item == null
                    && object == null || current.item != null
                    && current.item.equals(object)) {
                if (size == 1) {
                    firstNode = null;
                    lastNode = null;
                } else if (current == firstNode) {
                    firstNode = current.next;
                    firstNode.prev = null;
                } else if (current == lastNode) {
                    lastNode = current.prev;
                    lastNode.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
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
        return size == 0;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
