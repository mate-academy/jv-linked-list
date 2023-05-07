package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
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
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> node = getNodeIndex(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            node.prev.next = newNode;
            node.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T e : list) {
            add(e);
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return getNodeIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = getNodeIndex(index);
        T item = oldNode.item;
        oldNode.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        Node<T> nodeToRemove = getNodeIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (isEquals(object, node)) {
                unlink(node);
                return true;
            }
            node = node.next;
        }
        return false;

    }

    private boolean isEquals(T object, Node<T> node) {
        return object == node.item || object != null && object.equals(node.item);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

    private Node<T> getNodeIndex(int index) {
        isValidIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
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
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void isValidIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

}
