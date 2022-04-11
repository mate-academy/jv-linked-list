package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        size++;
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
            return;
        }
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = getNodeByIndex(index);
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        node.prev = newNode;
        size++;
        if (index == 0) {
            head = newNode;
            return;
        }
        prevNode.next = newNode;

    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T lastValue = node.value;
        node.value = value;
        return lastValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (object == current.value || object != null
                    && object.equals(current.value)) {
                unlink(current);
                return true;
            }
            current = current.next;
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
        Node<T> newNode;
        if (index < size / 2) {
            newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        } else {
            newNode = tail;
            for (int i = 0; i < size - index - 1; i++) {
                newNode = newNode.prev;
            }
        }
        return newNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (size-- == 1) {
            tail = head = null;
            return;
        }
        if (prevNode == null) {
            head.next.prev = null;
            head = head.next;
            return;
        }
        if (nextNode == null) {
            tail.prev.next = null;
            tail = tail.prev;
            return;
        }
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;
        private int[] mas = new int[5];

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
