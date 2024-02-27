package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        insertNode(tail, value, null);
    }

    @Override
    public void add(T value, int index) {
        if (head == null || (head != null && index == size)) {
            validateIndexForAdding(index);
            add(value);
        } else {
            Node<T> node = getNode(index);
            insertNode(node.prev, value, node);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
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
        Node<T> node = getNode(index);
        T oldValue = node.value;
        unlinkNode(node);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node == null) {
            return false;
        }
        unlinkNode(node);
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

    private static class Node<E> {
        private E value;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void validateIndexExist(int index) {
        validateIndexToBound(index, size);
    }

    private void validateIndexForAdding(int index) {
        validateIndexToBound(index, size + 1);
    }

    private void validateIndexToBound(int index, int bound) {
        if (index < 0 || index >= bound) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " out of bounds for length " + size
            );
        }
    }

    private Node<T> getNode(int index) {
        validateIndexExist(index);
        Node<T> node;

        if (size / 2 > index) {
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

    private Node<T> getNode(T object) {
        Node<T> current = null;
        for (int i = 0; i < size; i++) {
            current = i == 0 ? head : current.next;
            if (current.value == object || current.value != null && current.value.equals(object)) {
                return current;
            }
        }
        return null;
    }

    private void insertNode(Node<T> prevNode, T value, Node<T> nextNode) {
        Node<T> node = new Node<>(prevNode, value, nextNode);
        if (node.prev == null) {
            head = node;
        } else {
            node.prev.next = node;
        }
        if (nextNode == null) {
            tail = node;
        } else {
            nextNode.prev = node;
        }
        size++;
    }

    private void unlinkNode(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
