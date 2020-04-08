package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.nextElement = newNode;
        }
        tail = newNode;

        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index > 0 && index < size) {
            Node<T> atIndexNode = head;
            for (int i = 0; i < index; i++) {
                atIndexNode = atIndexNode.nextElement;
            }
            atIndexNode.prevElement.nextElement
                    = new Node<>(atIndexNode.prevElement, value, atIndexNode);
            size++;
            return;
        }
        throw new IndexOutOfBoundsException(index + " Out of bounds");
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = head;
        if (index >= 0 && index < size) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.nextElement;
            }
            return currentNode.element;
        }
        throw new IndexOutOfBoundsException(index + " Out of bounds");
    }

    @Override
    public T set(T value, int index) {
        T result = get(index);
        Node<T> atIndexNode = head;
        if (isIndexExists(index)) {
            for (int i = 0; i < index; i++) {
                atIndexNode = atIndexNode.nextElement;
            }
            atIndexNode.element = value;
        }
        return result;
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            Node<T> atIndexNode = head;
            for (int i = 0; i < index; i++) {
                atIndexNode = atIndexNode.nextElement;
            }
            if (index == 0) {
                head = atIndexNode.nextElement;
            }
            if (index == size - 1) {
                tail = atIndexNode.prevElement;
            }
            if (index > 0 && index != size - 1) {
                atIndexNode.prevElement.nextElement = atIndexNode.nextElement;
                atIndexNode.nextElement.prevElement = atIndexNode.prevElement;
            }
            size--;
            return atIndexNode.element;
        }
        throw new IndexOutOfBoundsException(index + " Out of bounds");
    }

    @Override
    public boolean remove(T t) {
        Node<T> atIndexNode = head;
        for (int i = 0; i < size; i++) {
            if (t == atIndexNode.element || t != null && t.equals(atIndexNode.element)) {
                remove(i);
                return true;
            }
            atIndexNode = atIndexNode.nextElement;

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

    private boolean isIndexExists(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " Out of bounds");
        }
        return true;
    }

    private static class Node<E> {
        public E element;
        public Node<E> prevElement;
        public Node<E> nextElement;

        public Node(Node<E> prevElement, E element, Node<E> nextElement) {
            this.element = element;
            this.prevElement = prevElement;
            this.nextElement = nextElement;
        }
    }
}
