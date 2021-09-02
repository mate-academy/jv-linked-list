package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int INIT_SIZE = 0;
    Node<T> head;
    Node<T> tail;
    int size;
    int position;

    MyLinkedList() {
        size = INIT_SIZE;
    }

    @Override
    public void add(T value) {
        Node<T> currentElement = head;
        if (currentElement != null) {
            currentElement = getLastNode();
            Node<T> nextElement = new Node<T>(currentElement, value, null);
            currentElement.next = nextElement;
            tail = nextElement;
            size++;
            return;
        }
        initFirstNode(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> indexElement = getNode(index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private static class Node<T> {
        Node<T> prev;
        T element;
        Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    private void initFirstNode(T value) {
        Node<T> firstNode;
        firstNode = new Node<T>(null, value, null);
        head = firstNode;
        tail = firstNode;
        size++;
    }

    private Node<T> getLastNode() {
        Node<T> currentElement = head;
        while (currentElement.next != null) {
            currentElement = currentElement.next;
        }
        return currentElement;
    }

    public void checkIndex(int index) {
        if (index < 0 || index + 1 > size) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }

    public Node<T> getNode(int index) {
        Node<T> currentElement = head;
        for (int i = 0; i < index; i++) {
            currentElement = currentElement.next;
        }
        return currentElement;
    }
}
