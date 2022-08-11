package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            addFirst(newNode);
        }
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "LinkedList index: " + index + " out of bounds exception");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> nodeForIndex = findNode(index);
            Node<T> pred = nodeForIndex.prev;
            Node<T> newNode = new Node<>(pred, value, nodeForIndex);
            nodeForIndex.prev = newNode;
            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> indexNode = head;
        for (int i = 0; i < index; i++) {
            indexNode = indexNode.next;
        }
        T result = indexNode.item;
        indexNode.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> headNode = head;
        while (headNode != null) {
            if ((headNode.item == object)
                    || (headNode.item != null && headNode.item.equals(object))) {
                unlink(headNode);
                return true;
            }
            headNode = headNode.next;
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

    private void addFirst(Node<T> node) {
        head = node;
        tail = node;
    }

    private Node<T> findNode(int index) {
        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = head;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = tail;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        if (head == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = node.next;
            head.prev = null;
        } else if (node == tail) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "LinkedList index: " + index + " out of bounds exception");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.item = value;
            this.next = next;
        }
    }
}
