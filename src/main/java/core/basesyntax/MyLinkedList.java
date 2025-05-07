package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            Node<T> oldNode = findElement(index);
            Node<T> newNode = new Node<T>(oldNode.prev, value, oldNode);
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
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
        checkIndex(index, size - 1);
        return findElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> currentNode = findElement(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> currentNode = head;
        return unlink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.value == null || currentNode.value.equals(object)) {
                unlink(currentNode);
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

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> findElement(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void addFirst(T value) {
        Node<T> oldNode = head;
        head = new Node<T>(null, value, oldNode);
        if (tail == null) {
            tail = head;
        } else {
            oldNode.prev = head;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> oldNode = tail;
        tail = new Node<T>(oldNode, value, null);
        if (head == null) {
            head = tail;
        } else {
            oldNode.next = tail;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T value = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.value = null;
        size--;
        return value;
    }

    private void checkIndex(int index, int limit) {
        if (index < 0 || index > limit) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out or range for limit " + limit);
        }
    }
}
