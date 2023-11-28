package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkNodeToTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkNodeToTail(value);
        } else {
            linkNode(findNode(index), value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T data : list) {
            add(data);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        final T deletedValue;
        Node<T> node = findNode(index);
        deletedValue = node.value;
        node.value = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        return unLink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        return findEqualsNode(object);
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
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> findNode(int index) {
        checkValidPositionForIndex(index);
        return index > size >> 1 ? iterFromTail(index) : iterFromHead(index);
    }

    private void linkNodeToTail(T value) {
        Node<T> previousTail = tail;
        Node<T> newNode = new Node<>(previousTail, value, null);
        tail = newNode;
        if (previousTail == null) {
            head = newNode;
        } else {
            previousTail.next = newNode;
        }
        size++;
    }

    private void linkNode(Node<T> node, T value) {
        Node<T> newNode = new Node<>(value);
        if (node.prev == null) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            node.prev.next = newNode;
            node.prev = newNode;
            newNode.prev = node.prev;
            newNode.next = node;
        }
        size++;
    }

    private T unLink(Node<T> node) {
        final T value = node.value;
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
        return value;
    }

    private boolean findEqualsNode(T obj) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (obj == node.value || (node.value != null && node.value.equals(obj))) {
                unLink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private Node<T> iterFromHead(int index) {
        int currIndex = 0;
        Node<T> node = head;
        while (currIndex < index) {
            node = node.next;
            currIndex = currIndex + 1;
        }
        return node;
    }

    private Node<T> iterFromTail(int index) {
        int currIndex = size - 1;
        Node<T> node = tail;
        while (currIndex > index) {
            node = node.prev;
            currIndex = currIndex - 1;
        }
        return node;
    }

    private void checkValidPositionForIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + " is invalid index");
        }
    }
}
