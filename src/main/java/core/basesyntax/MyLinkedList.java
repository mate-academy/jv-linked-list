package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(value, null, null);
        if (isEmpty()) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> afterNode = getNode(index);
            Node<T> beforeNode = afterNode.prev;
            Node<T> newNode = new Node<>(value, beforeNode, afterNode);
            afterNode.prev = newNode;
            if (beforeNode == null) {
                head = newNode;
            } else {
                beforeNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        throwExceptionIfIndexOutOfBounds(index);
        Node<T> removeNode = getNode(index);
        Node<T> beforeNode = removeNode.prev;
        Node<T> afterNode = removeNode.next;
        if (beforeNode == null) {
            head = afterNode;
        } else {
            beforeNode.next = afterNode;
        }
        if (afterNode == null) {
            tail = beforeNode;
        } else {
            afterNode.prev = beforeNode;
        }
        --size;
        return removeNode.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (t == null && node.item == null
                    || node.item.equals(t)) {
                remove(i);
                return true;
            }
            node = node.next;
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
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private void throwExceptionIfIndexOutOfBounds(int index) {
        if (index < 0 || index > (size - 1)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        throwExceptionIfIndexOutOfBounds(index);
        boolean isIndexHigherOfHalfOfSize = index > size >> 1;
        int countTraverseMoves = isIndexHigherOfHalfOfSize ? size - 1 - index : index;
        Node<T> node = isIndexHigherOfHalfOfSize ? tail : head;
        for (int i = 0; i < countTraverseMoves; i++) {
            node = isIndexHigherOfHalfOfSize ? node.prev : node.next;
        }
        return node;
    }
}
