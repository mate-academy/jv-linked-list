package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int EMPTY_LIST_SIZE = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = EMPTY_LIST_SIZE;
    }

    private void indexCheck(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < EMPTY_LIST_SIZE) {
            throw new IndexOutOfBoundsException("Index is out of range");
        }
    }

    private Node<T> getNodeFromIndex(int index) {
        indexCheck(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public void add(T value) {
        Node<T> node = tail;
        Node<T> newNode = new Node<>(node, value, null);
        tail = newNode;
        if (size == EMPTY_LIST_SIZE) {
            head = newNode;
        } else {
            node.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of range");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> nodeFromIndex = getNodeFromIndex(index);
            Node<T> newNode = new Node<>(nodeFromIndex.prev, value, nodeFromIndex);
            if (index == 0) {
                head = newNode;
            } else {
                nodeFromIndex.prev.next = newNode;
                nodeFromIndex.prev = newNode;
            }
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
        indexCheck(index);
        return getNodeFromIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> nodeFromIndex = getNodeFromIndex(index);
        T oldValue = nodeFromIndex.value;
        nodeFromIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> nodeFromIndex = getNodeFromIndex(index);
        Node<T> prev = nodeFromIndex.prev;
        Node<T> next = nodeFromIndex.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        T value = nodeFromIndex.value;
        nodeFromIndex.prev = null;
        nodeFromIndex.next = null;
        return value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((object != null && object.equals(get(i))) || object == get(i)) {
                remove(i);
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
        return head == null;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
