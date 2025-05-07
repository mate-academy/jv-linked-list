package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private void insert(Node<T> nodeT, T value) {
        Node<T> previousNode = nodeT.prev;
        Node<T> node = new Node<>(previousNode,value, nodeT);
        previousNode.next = node;
        nodeT.prev = node;
    }

    private void insertFirst(T value) {
        Node<T> node = new Node<>(null,value, null);
        head = node;
        tail = node;
    }

    private void insertLast(T value) {
        Node<T> node = new Node<>(tail,value, null);
        tail.next = node;
        tail = node;
    }

    private void insertHead(T value) {
        Node<T> nextNode = head;
        Node<T> node = new Node<>(null, value, nextNode);
        nextNode.prev = node;
        head = node;
    }

    private Node<T> iterate(int index) {
        return (index < size - index) ? headIterator(index) : tailIterator(index);
    }

    private Node<T> headIterator(int index) {
        Node<T> nodeT = head;
        for (int i = 0; i < index; i++) {
            nodeT = nodeT.next;
        }
        return nodeT;
    }

    private Node<T> tailIterator(int index) {
        Node<T> nodeT = tail;
        for (int i = 0; i < size - index - 1; i++) {
            nodeT = nodeT.prev;
        }
        return nodeT;
    }

    public void unlink(Node node) {
        Node previous = node.prev;
        Node next = node.next;
        if (previous == null && size != 1) {
            next.prev = null;
            head = next;
        } else if (next == null && size != 1) {
            previous.next = null;
            tail = previous;
        } else if (size == 1) {
            head.prev = null;
            head.next = null;
            head.value = null;
        } else {
            previous.next = next;
            next.prev = previous;
        }
    }

    private void checkIndex(int index, boolean dependence) {
        if (dependence || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            insertFirst(value);
        } else {
            insertLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, index > size);
        if (index == size) {
            add(value);
        } else if (index == 0 && size != 0) {
            insertHead(value);
            size++;
        } else {
            insert(iterate(index), value);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, index >= size);
        return iterate(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, index >= size);
        Node<T> nodeT = iterate(index);
        T oldValue = nodeT.value;
        nodeT.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node old = iterate(index);
        checkIndex(index, index >= size);
        unlink(old);
        size--;
        return (T)old.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeT = head;
        for (int i = 0; i < size; i++) {
            if (nodeT.value == object || nodeT.value != null && nodeT.value.equals(object)) {
                unlink(nodeT);
                size--;
                return true;
            } else {
                nodeT = nodeT.next;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node prev, T value, Node next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
