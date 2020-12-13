package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        addTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index!");
        }
        if (index == size) {
            addTail(value);
        } else {
            addHead(value, getNode(index));
        }
    }

    private void addTail(T value) {
        Node<T> lastNode = tail;
        Node<T> node = new Node<>(lastNode, value, null);
        tail = node;
        if (lastNode == null) {
            head = node;
        } else {
            lastNode.next = node;
        }
        size++;
    }

    private void addHead(T value, Node<T> node) {
        Node<T> nodeBefore = node.prev;
        Node<T> newNode = new Node<>(nodeBefore, value, node);
        node.prev = newNode;
        if (nodeBefore == null) {
            head = newNode;
        } else {
            nodeBefore.next = newNode;
        }
        size++;
    }

    public Node<T> getNode(int index) {
        checkSize(index);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node == tail) {
            tail.next = null;
            tail = node.prev;
        } else if (node == head) {
            head = node.next;
            head.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    public void checkSize(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index!");
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkSize(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkSize(index);
        Node<T> node = getNode(index);
        T newValue = node.value;
        node.value = value;

        return newValue;
    }

    @Override
    public T remove(int index) {
        checkSize(index);
        Node<T> node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = head;
        while (removeNode != null) {
            if ((object == null && removeNode.value == null) || (removeNode.value != null
                    && removeNode.value.equals(object))) {
                unlink(removeNode);
                return true;
            }
            removeNode = removeNode.next;
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
}
