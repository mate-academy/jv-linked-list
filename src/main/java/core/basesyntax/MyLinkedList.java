package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_EXCEPTION_MESSAGE = "Invalid index: %s";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = tail = new Node<>(null, value, null);
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeAtIndex = getNode(index);
        Node<T> newNode = new Node<>(nodeAtIndex.prev, value, nodeAtIndex);
        if (index == 0) {
            head = newNode;
        } else {
            nodeAtIndex.prev.next = newNode;
        }
        nodeAtIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if (Objects.equals(object, nodeToRemove.value)) {
                unlink(nodeToRemove);
                nodeToRemove.value = null;
                return true;
            }
            nodeToRemove = nodeToRemove.next;
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

    private void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format(INDEX_EXCEPTION_MESSAGE, index));
        }
    }

    private Node<T> getNode(int index) {
        validateIndex(index);
        return index <= size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format(INDEX_EXCEPTION_MESSAGE, index));
        }
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> node = tail;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }

        Node<T> prev = node.prev;
        Node<T> next = node.next;
        head = (prev == null) ? next : head;
        tail = (next == null) ? prev : tail;

        if (prev != null) {
            prev.next = next;
        }
        if (next != null) {
            next.prev = prev;
        }

        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}

