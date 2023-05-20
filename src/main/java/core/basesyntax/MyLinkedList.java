package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {

    }

    @Override
    public void add(T value) {
        if (head == null) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
            size++;
            return;
        }

        Node<T> prev = tail;
        Node<T> node = new Node<>(prev, value, null);
        tail = node;
        prev.next = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size && index >= 0) {
            add(value);
            return;
        }
        indexChecking(index);

        Node<T> nodeByIndex = getNodeByIndex(index);
        Node<T> node;
        if (index == 0) {
            node = new Node<>(null, value, nodeByIndex);
            nodeByIndex.prev = node;
            head = node;
            size++;
            return;
        }

        node = new Node<>(nodeByIndex.prev, value, nodeByIndex);
        nodeByIndex.prev.next = node;
        nodeByIndex.prev = node;
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
        indexChecking(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexChecking(index);
        Node<T> node = getNodeByIndex(index);
        T oldNodeItem = node.item;
        node.item = value;
        return oldNodeItem;
    }

    @Override
    public T remove(int index) {
        indexChecking(index);
        return unlink(getNodeByIndex(index), index).item;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            Node<T> nodeToRemove = getNodeByIndex(i);
            if (object != null && object.equals(nodeToRemove.item)
                    || object == null && nodeToRemove.item == null) {
                unlink(nodeToRemove, i);
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }

        node = tail;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private Node<T> unlink(Node<T> node, int index) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return node;
        }
        if (index == 0) {
            head = node.next;
            node.next.prev = null;
            size--;
            return node;
        }
        if (index == size - 1) {
            tail = node.prev;
            node.prev.next = null;
            size--;
            return node;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node;
    }

    private void indexChecking(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be less than zero");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index should be less than list's size "
                    + size);
        }
    }

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
