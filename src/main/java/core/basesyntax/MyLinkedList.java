package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

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
        } else {
            Node<T> node = getNode(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            if (node.prev != null) {
                node.prev.next = newNode;
            } else {
                head = newNode;
            }
            node.prev = newNode;
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T oldVal = current.value;
        current.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        unLink(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (Objects.equals(node.value, object)) {
                unLink(node);
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index < size / 2) {
            Node<T> newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        } else {
            Node<T> newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
            return newNode;
        }
    }

    private void unLink(Node<T> node) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
    }
}
