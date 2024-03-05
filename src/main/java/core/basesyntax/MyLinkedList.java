package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

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
        if (index == size) {
            add(value);
            return;
        }
        Node<T> findNode = find(index);
        Node<T> newNode = new Node<>(findNode.prev, value, findNode);
        if (findNode.prev != null) {
            findNode.prev.next = newNode;
        }
        findNode.prev = newNode;
        if (findNode.equals(head)) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        Node<T> findNode = find(index);
        return findNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> findNode = find(index);
        T oldValue = findNode.item;
        findNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> findNode = find(index);
        unlink(findNode);
        T oldValue = findNode.item;
        findNode.item = null;
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> findNode = head;
        for (int i = 0; i < size; i++) {
            if (object != null && object.equals(findNode.item) || object == findNode.item) {
                remove(i);
                return true;
            }
            findNode = findNode.next;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Specified index is invalid: "
                    + index + " >= " + size);
        }
    }

    private Node<T> find(int index) {
        checkIndex(index);
        Node<T> findNode = head;
        if (index <= size / 2) {
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

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
