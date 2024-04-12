package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = getNodeWithIndex(index);
        checkIsNodeEqualsNull(node);
        Node<T> nodeToAdd = new Node<>(node.prev, value, node);

        if (node.prev == null) {
            head = nodeToAdd;
        } else {
            node.prev.next = nodeToAdd;
        }

        if (node.next == null) {
            tail = nodeToAdd;
        }
        node.prev = nodeToAdd;
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
        Node<T> node = getNodeWithIndex(index);
        checkIsNodeEqualsNull(node);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeWithIndex(index);
        checkIsNodeEqualsNull(node);
        T previousValue = node.value;
        node.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeWithIndex(index);
        return unlinkNode(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (compareValues(node.value, object)) {
                break;
            }
            node = node.next;
        }
        if (node == null) {
            return false;
        }
        unlinkNode(node);
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

    private Node<T> getNodeWithIndex(int index) {
        int counter = 0;
        Node<T> node = head;
        while (node != null) {
            if (counter == index) {
                break;
            }
            node = node.next;
            counter++;
        }
        return node;
    }

    private T unlinkNode(Node<T> node) {
        checkIsNodeEqualsNull(node);
        final T value = node.value;

        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size--;
        return value;
    }

    private static <T> boolean compareValues(T a, T b) {
        return (a == b) || (a != null && a.equals(b));
    }

    private static <T> void checkIsNodeEqualsNull(Node<T> node) {
        if (node == null) {
            throw new ArrayIndexOutOfBoundsException("");
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }

        public Node(T value) {
            this.value = value;
        }
    }

}
