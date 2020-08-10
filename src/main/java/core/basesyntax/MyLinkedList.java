package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node head;
    private Node tail;

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(value, null, null);
        if (isEmpty()) {
            linkHead(node);
            linkTail(node);
            size++;
            return true;
        }
        tail.next = node;
        node.prev = tail;
        linkTail(node);
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = new Node(value, null, null);
        if (index == 0) {
            if (isEmpty()) {
                add(value);
                return;
            }
            head.prev = node;
            node.next = head;
            linkHead(node);
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        node.next = getNode(index);
        node.prev = getNode(index - 1);
        getNode(index).prev = node;
        getNode(index - 1).next = node;
        size++;
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
        return (T) getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T prevValue = get(index);
        getNode(index).value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        T removedElement = get(index);
        if (index == 0) {
            linkHead(getNode(index).next);
            size--;
            return removedElement;
        }
        if (index == size - 1) {
            linkTail(getNode(index).prev);
            size--;
            return removedElement;
        }
        getNode(index + 1).prev = getNode(index - 1);
        getNode(index - 1).next = getNode(index + 1);
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).value == t || getNode(i).value.equals(t)) {
                remove(i);
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

    private void linkHead(Node node) {
        head = node;
        if (node != null) {
            node.prev = null;
        }
    }

    private void linkTail(Node node) {
        tail = node;
        if (node != null) {
            node.next = null;
        }
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index!");
        }
        if (size - index > size / 2) {
            int i = 0;
            Node element = head;
            while (i < index) {
                element = element.next;
                i++;
            }
            return element;
        } else {
            int i = size - 1;
            Node element = tail;
            while (i > index) {
                element = element.prev;
                i--;
            }
            return element;
        }
    }

    private class Node<T> {
        private T value;
        private Node next;
        private Node prev;

        public Node(T value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
