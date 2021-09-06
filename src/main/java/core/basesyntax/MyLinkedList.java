package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size != 0) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
            return;
        }
        initFirstNode(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> indexNode = getNode(index);
        if (index != 0) {
            linkNodes(indexNode.prev, newNode);
        } else {
            head = newNode;
        }
        linkNodes(newNode, indexNode);
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
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> indexNode = getNode(index);
        T oldValue = indexNode.value;
        indexNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> indexNode = getNode(index);
        return unlinkNode(indexNode).value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        return unlinkNode(node) != null;
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

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void initFirstNode(T value) {
        Node<T> firstNode = new Node<>(null, value, null);
        head = firstNode;
        tail = firstNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || (index >= size)) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> getNode(T value) {
        Node<T> currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.value == null ? value == null : currentNode.value.equals(value)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return currentNode.value.equals(value) ? currentNode : null;
    }

    private void linkNodes(Node<T> current, Node<T> next) {
        if (current != null) {
            current.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = current;
        } else {
            tail = current;
        }
    }

    private Node<T> unlinkNode(Node<T> node) {
        if (node != null) {
            linkNodes(node.prev, node.next);
            size--;
            return node;
        }
        return null;
    }
}
