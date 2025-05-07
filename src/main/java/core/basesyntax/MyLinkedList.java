package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = tail = new Node<>(null, value, null);
        } else {
            tail = tail.next = new Node<>(tail, value, null);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nextNode = getNode(index);
        Node<T> prevNextNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNextNode, value, nextNode);
        nextNode.prev = newNode;
        if (prevNextNode != null) {
            prevNextNode.next = newNode;
        } else {
            head = newNode;
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> changeNode = getNode(index);
        T changeNodeValue = changeNode.value;
        changeNode.value = value;
        return changeNodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        unLink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (compare(current.value, object)) {
                unLink((current));
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

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Requested index" + index + " is out of range");
        }
    }

    private boolean compare(T a, T b) {
        return a == b || a != null && a.equals(b);
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);
        Node<T> current;
        if ((size >> 1) > index) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > (index + 1); i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unLink(Node<T> node) {
        size--;
        if (node.next == null) {
            if (node.prev != null) {
                node.prev.next = null;
            }
            tail = node.prev;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }

    private static class Node<T> {

        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
