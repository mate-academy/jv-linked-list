package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;

        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkElementIndex(index);

        if (index == size) {
            add(value);
        } else {
            Node<T> currentElement = findElement(index);
            Node<T> prevLink = currentElement.prev;
            Node<T> newNode = new Node<>(prevLink, value, currentElement);
            currentElement.prev = newNode;

            if (prevLink == null) {
                first = newNode;
            } else {
                prevLink.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t: list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return findElement(index).item;
    }

    @Override
    public void set(T value, int index) {
        checkPositionIndex(index);
        findElement(index).item = value;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> removedElement = findElement(index);
        Node<T> prevLink = removedElement.prev;
        Node<T> nextLink = removedElement.next;

        if (prevLink == null) {
            first = nextLink;
        } else {
            prevLink.next = nextLink;
            removedElement.prev = null;
        }

        if (nextLink == null) {
            last = prevLink;
        } else {
            nextLink.prev = prevLink;
            removedElement.next = null;
        }

        T removedItem = removedElement.item;
        removedElement.item = null;
        size--;

        return removedItem;
    }

    @Override
    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            if (t == findElement(i) || t.equals(findElement(i).item)) {
                T removedElement = findElement(i).item;
                remove(i);
                return removedElement;
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> findElement(int index) {
        if (index < (size >> 1)) {
            Node<T> fromBegin = first;
            for (int i = 0; i < index; i++) {
                fromBegin = fromBegin.next;
            }
            return fromBegin;
        } else {
            Node<T> fromEnd = last;
            for (int i = size - 1; i > index; i--) {
                fromEnd = fromEnd.prev;
            }
            return fromEnd;
        }
    }
}
