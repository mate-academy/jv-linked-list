package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(null, value, null);
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
        if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
        } else {
            Node<T> validNode = getNodeByIndex(index);
            Node<T> newNode = new Node<T>(validNode.prev, value, validNode);
            validNode.prev = newNode;
            newNode.next.prev = newNode;
        }
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNodeByIndex(index);
        unlink(target);
        return target.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> targetNode = getNodeByValue(object);
        if (targetNode == null) {
            return false;
        }
        unlink(targetNode);
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

    public void unlink(Node<T> target) {
        Node<T> prevNode = target.prev;
        Node<T> nextNode = target.next;
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
        size--;
    }

    private Node<T> getNodeByIndex(int targetIndex) {
        if (targetIndex < 0 || targetIndex >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index incorrect! Index: %s, Size: %s", targetIndex, size));
        }
        Node<T> currentNode = tail;
        for (int i = size - 1; i != targetIndex; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == value || node.value != null && node.value.equals(value)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }
}

