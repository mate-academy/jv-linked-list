package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        Node nextNode = getNode(index);
        Node prevNode = nextNode.prev;
        Node node = new Node(prevNode, value, nextNode);
        if (isHead(index)) {
            head = node;
        } else if (isTail(index - 1)) {
            tail = node;
        } else {
            prevNode.next = node;
            nextNode.prev = node;
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
        checkExistedIndex(index);
        Node node = getNode(index);
        return (T) node.value;
    }

    @Override
    public T set(T value, int index) {
        checkExistedIndex(index);
        Node node = getNode(index);
        T oldValue = (T) node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkExistedIndex(index);
        Node removedNode = getNode(index);
        T value = (T) removedNode.value;
        if (isHead(index) && isTail(index)) {
            head = tail = null;
        } else if (isHead(index)) {
            Node newHead = head.next;
            head = newHead;
        } else if (isTail(index)) {
            Node newTail = tail.prev;
            newTail.next = null;
            tail = newTail;
        } else {
            Node prev = removedNode.prev;
            Node next = removedNode.next;
            prev.next = next;
            next.prev = prev;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        for (int i = 0; i < size; i++) {
            T value = (T) current.value;
            if (value == (T) object
                    || value != null && value.equals((T) object)
            ) {
                remove(i);
                return true;
            }
            current = current.next;
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

    private Node getNode(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkExistedIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound");
        }
    }

    private static boolean isHead(int index) {
        return index == 0;
    }

    private boolean isTail(int index) {
        return index == size - 1;
    }

    private static class Node<T> {
        private T value;
        private Node prev;
        private Node next;

        public Node() {
        }

        public Node(T value) {
            this.value = value;
        }

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
