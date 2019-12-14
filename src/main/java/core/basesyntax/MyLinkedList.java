package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        Node(T element) {
            this.element = element;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        this.size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
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
    public void add(T value, int index) throws ArrayIndexOutOfBoundsException {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> nodeByIndex = searchNodeByIndex(index);
        Node<T> newNode = new Node<>(value);
        newNode.prev = nodeByIndex.prev;
        newNode.next = nodeByIndex;
        nodeByIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) throws ArrayIndexOutOfBoundsException {
        checkIndex(index);
        return searchNodeByIndex(index).element;
    }

    @Override
    public void set(T value, int index) throws ArrayIndexOutOfBoundsException {
        checkIndex(index);
        Node<T> nodeByIndex = searchNodeByIndex(index);
        nodeByIndex.element = value;
    }

    @Override
    public T remove(int index) throws ArrayIndexOutOfBoundsException {
        checkIndex(index);
        return removeNode(searchNodeByIndex(index));
    }

    @Override
    public T remove(T t) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (t == temp.element || t != null && t.equals(temp.element)) {
                size--;
                return temp.element;
            } else {
                temp = temp.next;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index is less than 0");
        }
    }

    private Node<T> searchNodeByIndex(int index) {
        if ((size >> 1) >= index) {
            Node<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp;
        } else {
            Node<T> temp = tail;
            for (int i = 0; i < size - index - 1; i++) {
                temp = temp.prev;
            }
            return temp;
        }
    }

    private T removeNode(Node<T> node) {
        if (node == head) {
            if (size == 1) {
                T headElement = head.element;
                size--;
                return headElement;
            }
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node.element;
    }
}



