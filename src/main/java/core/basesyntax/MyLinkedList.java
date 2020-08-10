package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int sizeList;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (sizeList == 0) {
            head = newNode;
            tail = newNode;
            sizeList++;
            return true;
        } else {
            Node<T> node = new Node<>(value, null, tail);
            tail.next = node;
            tail = node;
        }
        sizeList++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == sizeList) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value, head, null);
            head.prev = newNode;
            head = newNode;
            sizeList++;
            return;
        }
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(value, node, node.prev);
        node.prev.next = newNode;
        node.prev = newNode;
        sizeList++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        if (index == sizeList - 1) {
            if (index == 0) {
                head = null;
                tail = null;
                sizeList--;
                return node.value;
            }
            node.prev.next = null;
            tail = node.prev;
            sizeList--;
            return node.value;
        }
        if (index == 0) {
            node.next.prev = null;
            head = node.next;
            sizeList--;
            return node.value;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        sizeList--;
        return node.value;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < sizeList; i++) {
            if (t == getNode(i).value || getNode(i).value.equals(t)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return sizeList;
    }

    @Override
    public boolean isEmpty() {
        return sizeList == 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= sizeList) {
            throw new IndexOutOfBoundsException("Check index!");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
