package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        createNode(tail, value, null);
    }

    @Override
    public void add(T value, int index) {
        if (head == null || (head != null && index == size)) {
            validateIndexForAdding(index);
            add(value);
        } else {
            Node<T> node = findNodeByIndex(index);
            createNode(node.prev, value, node);
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
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        unlink(node);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = findNode(object);
        if (node == null) {
            return false;
        }
        unlink(node);
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

    private Node<T> findNode(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private Node<T> findNodeByIndex(int index) {
        validateIndexExist(index);
        if (size / 2 > index) {
            return findFromHead(index);
        } else {
            return findFromTail(index);
        }
    }

    private Node<T> findFromHead(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node<T> findFromTail(int index) {
        Node<T> node = tail;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private void createNode(Node<T> prevNode, T value, Node<T> nextNode) {
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

    private void unlink(Node<T> node) {
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
}
