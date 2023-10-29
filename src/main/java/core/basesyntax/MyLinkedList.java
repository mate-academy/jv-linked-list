package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(tail, value, null);
        Node<T> temp = tail;
        tail = newNode;
        if (temp == null) {
            head = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkPositionIndex(index);
            Node<T> foundNode = getNode(index);
            Node<T> prev = foundNode.prev;
            Node<T> newNode = new Node<T>(prev, value, foundNode);
            foundNode.prev = newNode;
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> foundNode = getNode(index);
        T oldValue = foundNode.value;
        foundNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> foundNode = getNode(index);
        return unlink(foundNode);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value != null && node.value.equals(object)) {
                unlink(node);
                return true;
            }
            if (node.value == null && object == null) {
                unlink(node);
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

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> nextNode;
        if (index < (size / 2)) {
            nextNode = head;
            for (int i = 0; i < index; i++) {
                nextNode = nextNode.next;
            }
        } else {
            nextNode = tail;
            for (int i = size - 1; i > index; i--) {
                nextNode = nextNode.prev;
            }
        }
        return nextNode;
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
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

        node.value = null;
        size--;
        return element;
    }
}

