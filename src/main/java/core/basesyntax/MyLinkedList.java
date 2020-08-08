package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            first = new Node<>(value, last, null);
            size++;
            return true;
        }
        if (size == 1) {
            last = new Node<>(value, null, first);
            first.next = last;
            size++;
            return true;
        }
        last.next = new Node<>(value, null, last);
        last = last.next;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "This is index either negative or "
                            + "bigger than the actual size of list");
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value, first, null);
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> currentFirstNode = findNode(index - 1);
            Node<T> currentLastNode = currentFirstNode.next;
            currentFirstNode.next = new Node<>(value, currentLastNode, currentFirstNode);
            currentLastNode.prev = currentFirstNode.next;
            size++;
        }
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
        isIndexSuits(index);
        Node<T> currentNode = findNode(index);
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        isIndexSuits(index);
        Node<T> currentNode = findNode(index);
        T returnElement = currentNode.element;
        currentNode.element = value;
        return returnElement;
    }

    @Override
    public T remove(int index) {
        isIndexSuits(index);
        if (index == 0) {
            return removeFirstElement();
        }
        if (index == size - 1) {
            return removeLastElement();
        }
        Node<T> currentNode = findNode(index);
        Node<T> cupNode = currentNode.prev;
        cupNode.next = currentNode.next;
        cupNode = currentNode.next;
        cupNode.prev = currentNode.prev;
        size--;
        return currentNode.element;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (get(i) == t || get(i) != null && get(i).equals(t)) {
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

    private T removeLastElement() {
        final T removedValue = last.element;
        last = last.prev;
        last.next = null;
        size--;
        return removedValue;
    }

    private T removeFirstElement() {
        T removedValue = first.element;
        if (size == 1) {
            first = null;
            last = null;
            size--;
            return removedValue;
        }
        first = first.next;
        first.prev = null;
        size--;
        return removedValue;
    }

    private Node<T> findNode(int index) {
        int currentSize = 0;
        Node<T> currentNode = first;
        while (currentSize < index) {
            currentNode = currentNode.next;
            currentSize++;
        }
        return currentNode;
    }

    private void isIndexSuits(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "This is index either negative "
                            + "or bigger than the actual size of the list");
        }
    }

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.element = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
