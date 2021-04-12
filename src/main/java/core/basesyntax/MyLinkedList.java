package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.previous = prev;
        }
    }

    @Override
    public boolean add(T value) {
        linkNode(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkNode(value);
        } else {
            linkNode(value, getNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list.size() == 0) {
            return false;
        }
        for (T object : list) {
            add(object);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (object == null && current.element == null
                    || object != null && object.equals(current.element)) {
                unlink(current);
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

    private void linkNode(T value) {
        Node<T> currentLastNode = tail;
        Node<T> newNode = new Node<>(currentLastNode, value, null);
        tail = newNode;
        if (currentLastNode != null) {
            currentLastNode.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    private void linkNode(T value, Node<T> nearestNode) {
        Node<T> previousNode = nearestNode.previous;
        Node<T> newNode = new Node<>(previousNode, value, nearestNode);
        nearestNode.previous = newNode;
        if (previousNode != null) {
            previousNode.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);
        if (index < (size / 2)) {
            Node<T> newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        }
        Node<T> newNode = tail;
        for (int i = size - 1; i > index; i--) {
            newNode = newNode.previous;
        }
        return newNode;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private T unlink(Node<T> currentNode) {
        final T element = currentNode.element;
        final Node<T> next = currentNode.next;
        final Node<T> previous = currentNode.previous;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            currentNode.previous = null;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            currentNode.next = null;
        }

        currentNode.element = null;
        size--;
        return element;
    }
}
