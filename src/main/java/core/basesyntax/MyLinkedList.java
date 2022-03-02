package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node head, tail;

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            createFirstNode(value);
        } else {
            Node<T> node = new Node<>(value,null, null);
            if (size == 1) {
                head.next = tail;
            }
            insertNode(node);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index can't be "
                    + "less than 0 or greater than LinkedList size");
        }
        Node<T> node;
        if (index == size) {
            if (isEmpty()) {
                createFirstNode(value);
            } else {
                node = new Node<>(value, null, null);
                insertNode(node);
            }
        } else {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            Node<T> newNode = new Node<>(value, node.prev, node);
            insertNode(newNode, index);
        }
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
        checkIndex(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        Node<T> nodeCopy = node;
        removeNode(node, index);
        return nodeCopy.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value == object
                    || (node.value != null
                    && node.value.equals(object))) {
                removeNode(node, i);
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

    private void createFirstNode(T value) {
        tail = new Node<>(value, null, null);
        head = tail;
    }

    private void insertNode(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index can't be "
                    + "less than 0 or greater than LinkedList size");
        }
    }

    private void removeNode(Node<T> node, int index) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (index == 0) {
            head = node.next;
        } else if (index == size - 1) {
            tail = node.prev;
        }
        size--;
    }

    private void insertNode(Node<T> node, int index) {
        if (node.prev != null) {
            node.prev.next = node;
        }
        if (node.next != null) {
            node.next.prev = node;
        }
        if (index == 0) {
            head = node;
        } else if (index == size) {
            tail = node;
        }
    }
}
