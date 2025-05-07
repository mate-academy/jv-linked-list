package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != 0) {
            isIndexInRange(index - 1);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeByIndex = findByIndex(index);
        Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
        if (nodeByIndex.prev == null) {
            head = newNode;
        } else {
            nodeByIndex.prev.next = newNode;
        }
        nodeByIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        isIndexInRange(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        isIndexInRange(index);
        Node<T> currentNode = findByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexInRange(index);
        Node<T> currentNode = findByIndex(index);
        T removedValue = currentNode.value;
        unlink(currentNode);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T value) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == value || node.value.equals(value)) {
                unlink(node);
                size--;
                return true;
            }
            node = node.next;
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

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E value, Node<E> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private void isIndexInRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index" + index);
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
        node.value = null;
    }
}
