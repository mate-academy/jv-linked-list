package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> currentNode = getIndex(index);
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
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getIndex(index);
        T firstValue = node.value;
        node.value = value;
        return firstValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedNode = head;
        for (int i = 0; i < size; i++) {
            if (object != null && object.equals(deletedNode.value) || deletedNode.value == object) {
                remove(i);
                return true;
            }
            deletedNode = deletedNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is not correct");
        }
    }

    private Node<T> getIndex(int index) {
        Node<T> foundNode;
        if (index < size / 2) {
            foundNode = head;
            for (int i = 0; i < index; i++) {
                foundNode = foundNode.next;
            }
        } else {
            foundNode = tail;
            for (int i = size - 1; i > index; i--) {
                foundNode = foundNode.prev;
            }
        }
        return foundNode;
    }

    private T unlink(Node<T> node) {
        final T deletedNode = node.value;
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        size--;
        return deletedNode;
    }
}
