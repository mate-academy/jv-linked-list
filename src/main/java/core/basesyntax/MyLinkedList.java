package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private static class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == this.size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, this.head);
            this.head.previous = newNode;
            this.head = newNode;
            size++;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.previous, value, nodeByIndex);
            nodeByIndex.previous.next = newNode;
            nodeByIndex.previous = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T i : list) {
            this.add(i);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T previousValue = node.value;
        node.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        T nodeValue = node.value;
        removeNode(node);
        return nodeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> thisNode = this.head;
        while (thisNode != null) {
            if (thisNode.value == object
                    || thisNode.value != null
                    && thisNode.value.equals(object)) {
                removeNode(thisNode);
                return true;
            }
            thisNode = thisNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void removeNode(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }

        if (node == tail) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
    }
}
