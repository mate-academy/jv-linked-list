package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private int size;
    private Node<T> tail;

    public MyLinkedList() {
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index, size);
        if (index == size) {
            add(value);
        }
        Node<T> currentNode = getNode(index);
        Node<T> prevNode = currentNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, currentNode);
        currentNode.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
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
        indexCheck(index, size - 1);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index, size - 1);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index, size - 1);
        Node<T> node = getNode(index);
        final T value = node.item;
        Node<T> next = node.next;
        Node<T> prev = node.prev;
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
        node.item = null;
        size--;
        return value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = head;
        int index = 0;
        while (currentNode != null) {
            if (currentNode.item == t || currentNode.item != null && currentNode.item.equals(t)) {
                remove(index);
                return true;
            }
            currentNode = currentNode.next;
            index++;
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

    private Node<T> getNode(int index) {
        int from;
        if (index < size >> 1) {
            from = 0;
        } else {
            from = index;
        }
        int to;
        if (index < size >> 1) {
            to = index;
        } else {
            to = size - 1;
        }
        Node<T> node;
        if (from == 0) {
            node = head;
        } else {
            node = tail;
        }
        for (int i = from; i < to; i++) {
            node = from == 0 ? node.next : node.prev;
        }
        return node;
    }

    public void indexCheck(int indexToCheck, int conToCheck) {
        if (indexToCheck < 0 || indexToCheck > conToCheck) {
            throw new IndexOutOfBoundsException();
        }
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
