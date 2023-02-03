package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        private final T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
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
            this.add(value);
        } else {
            Node<T> newNode = findByIndex(index);
            linkNode(newNode, value);
        }
    }

    private void linkNode(Node<T> nextNode, T valueOfNewNode) {
        if (nextNode.equals(head)) {
            nextNode = new Node<>(null, valueOfNewNode, head);
            head.prev = nextNode;
            head = nextNode;
        } else {
            nextNode = new Node<>(nextNode.prev, valueOfNewNode, nextNode);
            nextNode.prev.next = nextNode;
            nextNode.next.prev = nextNode;
        }
        size++;
    }

    private Node<T> findByIndex(int index) {
        Node<T> tempNode;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounds for the List size " + size);
        } else if (index == 0) {
            return head;
        } else if (index < size >> 1) {
            tempNode = head;
            while (index > 0) {
                tempNode = tempNode.next;
                index--;
            }
        } else {
            tempNode = tail;
            while (index < size - 1) {
                tempNode = tempNode.prev;
                index++;
            }
        }
        return tempNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            this.add(value);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = findByIndex(index);
        unlinkNode(newNode);
        add(value, index);
        return newNode.value;
    }

    @Override
    public T remove(int index) {
        Node<T> newNode = findByIndex(index);
        unlinkNode(newNode);
        return newNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = findByValue(object);
        if (newNode != null) {
            unlinkNode(newNode);
        }
        return newNode != null;
    }

    private void unlinkNode(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node.equals(head)) {
            head.next.prev = null;
            head = head.next;
        } else if (node.equals(tail)) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private Node<T> findByValue(T object) {
        Node<T> tempNode = head;
        while (tempNode != null) {
            if (object == null && tempNode.value == null
                    || tempNode.value != null && tempNode.value.equals(object)) {
                return tempNode;
            }
            tempNode = tempNode.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}


