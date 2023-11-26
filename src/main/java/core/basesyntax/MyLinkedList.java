package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> tail;
    private Node<T> head;
    private int size;

    static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.element = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> nodeBeforeIndex = getNodeByIndex(index - 1);
            Node<T> newNode = new Node<>(value);
            newNode.prev = nodeBeforeIndex;
            newNode.next = nodeBeforeIndex.next;
            nodeBeforeIndex.next.prev = newNode;
            nodeBeforeIndex.next = newNode;
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
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        return node.element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.element;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            return false;
        }

        boolean removed = false;
        Node<T> current = head;
        while (current != null) {
            Node<T> nextNode = current.next;
            if (object == null ? current.element == null : object.equals(current.element)) {
                unlink(current);
                removed = true;
                break;
            }
            current = nextNode;
        }
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> indexNode = head;
        for (int i = 0; i < index; i++) {
            indexNode = indexNode.next;
        }
        return indexNode;
    }

    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
    }
}
