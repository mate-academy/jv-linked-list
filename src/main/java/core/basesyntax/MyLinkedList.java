package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DIVIDE = 2;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<>(prevNode, value, null);
        tail = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(null, value, head);
            size++;
        } else {
            Node<T> node = getNode(index);
            Node<T> prevNode = node.prev;
            Node<T> newNode = new Node<>(prevNode, value, node);
            prevNode.next = newNode;
            node.prev = newNode;
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return deleteNode(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.item == object
                    || node.item != null && node.item.equals(object)) {
                deleteNode(node);
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < (size / DIVIDE)) {
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

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private T deleteNode(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return node.item;
    }
}
