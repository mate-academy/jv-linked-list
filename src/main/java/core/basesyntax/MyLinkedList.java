package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
    }

    private static class Node<T> {
        private T value;
        private Node next;
        private Node prev;

        private Node(T d) {
            prev = null;
            value = d;
            next = null;
        }

        private Node(T value, Node prev, Node next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node shiftedElement = findByIndex(index);
            Node newNode = new Node(value, shiftedElement.prev, shiftedElement);
            shiftedElement.prev = newNode;
            if (index == 0) {
                head = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return (T) findByIndex(index).value;
    }

    @Override
    public void set(T value, int index) {
        findByIndex(index).value = value;
    }

    @Override
    public T remove(int index) {
        Node removedNode = findByIndex(index);
        if (size == 1) {
            head = tail = null;
            size--;
            return (T) removedNode.value;
        }
        return removedNode == tail
                ? removeTail(removedNode) : removedNode == head
                ? removeHead(removedNode) : removeFromInside(removedNode, index);
    }

    @Override
    public T remove(T t) {
        int index = findByValue(t);
        if (index == size) {
            return null;
        }
        return remove(index);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private T removeTail(Node last) {
        tail.prev.next = null;
        tail = tail.prev;
        size--;
        return (T) last.value;
    }

    private T removeHead(Node first) {
        head.next.prev = null;
        head = head.next;
        size--;
        return (T) first.value;
    }

    private T removeFromInside(Node element, int index) {
        Node prevElement = findByIndex(index - 1);
        Node nextElement = findByIndex(index + 1);
        prevElement.next = nextElement;
        nextElement.prev = prevElement;
        size--;
        return (T) element.value;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("" + index + "out of bounds");
        }
    }

    private Node findByIndex(int index) {
        checkIndex(index);
        Node value;
        if (index < (size >> 1)) {
            value = head;
            for (int i = 0; i < index; i++) {
                value = value.next;
            }
            return value;
        }
        value = tail;
        for (int i = size - 1; i > index; i--) {
            value = value.prev;
        }
        return value;
    }

    private int findByValue(T value) {
        Node result = head;
        for (int i = 0; i < size; i++) {
            if (result.value == value || (value != null && value.equals(result.value))) {
                return i;
            }
            result = result.next;
        }
        return size;
    }
}

