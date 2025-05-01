package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        tail = new Node<T>(tail, value, null);
        if (tail.prev == null) {
            head = tail;
        } else {
            tail.prev.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> next = getNode(index);
            Node<T> newNode = new Node<>(null, value, next);
            if (next.prev == null) {
                head = newNode;
            } else {
                next.prev.next = newNode;
                next.prev = newNode;
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNode(index);
        T old = newNode.item;
        newNode.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("There is no such index in the list, " + index);
        }
        Node<T> removeNode;
        removeNode = getNode(index);
        if (removeNode.prev == null) {
            head = removeNode.next;
        } else {
            removeNode.prev.next = removeNode.next;
        }
        if (removeNode.next == null) {
            tail = removeNode.prev;
        } else {
            removeNode.next.prev = removeNode.prev;
        }
        removeNode.prev = null;
        removeNode.next = null;
        size--;
        return removeNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        int i = 0;
        while (!((currentNode.item == object)
                || (currentNode.item != null
                && currentNode.item.equals(object)))) {
            if (i == size - 1) {
                return false;
            }
            currentNode = currentNode.next;
            i++;
        }
        remove(i);
        return true;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("There is no such index in the list, " + index);
        }
    }

    public Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        if (index <= size / 2) {
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
