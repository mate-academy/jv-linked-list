package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> latestNode;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);

        if (size == 0) {
            head = node;
            tail = node;
        } else {
            tail = node;
            node.prev = latestNode;
            latestNode.next = node;
        }

        latestNode = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is not valid");
        }

        if (size != 0 && index != size) {
            if (index == 0) {
                Node<T> nextNode = findNodeByIndex(index);
                Node<T> node = new Node<>(null, value, nextNode);

                nextNode.prev = node;
                head = node;
                size++;
            } else {
                Node<T> prevNode = findNodeByIndex(index - 1);
                Node<T> node = new Node<>(prevNode, value, prevNode.next);

                prevNode.next.prev = node;
                prevNode.next = node;
                size++;
            }
        } else {
            add(value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> node = findNodeByIndex(index);
        T prevValue = node.value;
        node.value = value;

        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> node = findNodeByIndex(index);

        if (size == 1) {
            latestNode = null;
            head = null;
            tail = null;
        } else if (equals(node, tail)) {
            node.prev.next = null;
            tail = node.prev;
        } else if (equals(node, head)) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        Node<T> node = head;

        while (true) {
            if (equals(node.value, object)) {
                remove(index);
                return true;
            } else if (node.value == null && object == null) {
                remove(index);
                return true;
            } else if (node == tail) {
                return false;
            }

            node = node.next;
            index++;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findNodeByIndex(int index) {
        int i = 0;
        Node<T> node = head;
        while (i != index) {
            node = node.next;
            i++;
        }

        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is not valid");
        }
    }

    private boolean equals(Object o1, Object o2) {
        return o1 != null && o1.equals(o2);
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
}
