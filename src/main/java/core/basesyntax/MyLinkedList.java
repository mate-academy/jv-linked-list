package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index not found " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> requiredNode = searchNodeByIndex(index);
        Node<T> newNode = new Node<>(requiredNode.prev, value, requiredNode);
        newNode.next.prev = newNode;
        if (index == 0) {
            head = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> requiredNode = searchNodeByIndex(index);
        T oldValue = requiredNode.value;
        requiredNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> requiredNode = searchNodeByIndex(index);
        T removedNode = requiredNode.value;
        deleteNode(requiredNode);
        return removedNode;
    }

    @Override
    public boolean remove(T object) {
        Node<T> requiredNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null ? requiredNode.value == object : requiredNode.value.equals(object)) {
                deleteNode(requiredNode);
                return true;
            }
            requiredNode = requiredNode.next;
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
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index not found " + index);
        }
    }

    private Node<T> searchNodeByIndex(int index) {
        checkIndex(index);
        Node<T> requiredNode;
        if (index < size / 2) {
            requiredNode = head;
            for (int i = 0; i < index; i++) {
                requiredNode = requiredNode.next;
            }
        } else {
            requiredNode = tail;
            for (int i = size - 1; i > index; i--) {
                requiredNode = requiredNode.prev;
            }
        }
        return requiredNode;
    }

    private void deleteNode(Node<T> requiredNode) {
        if (requiredNode == head) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
        } else if (requiredNode == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            requiredNode.next.prev = requiredNode.prev;
            requiredNode.prev.next = requiredNode.next;
        }
        size--;
    }
}
