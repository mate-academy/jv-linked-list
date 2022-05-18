package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index entered. Index: "
                    + index + " list size: " + size);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = searchNodeByIndex(index);
            linkBefore(value, currentNode);
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
        checkIndex(index);
        return searchNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = searchNodeByIndex(index);
        T replacedItem = currentNode.item;
        currentNode.item = value;
        return replacedItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = searchNodeByIndex(index);
        return unlink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.item || object != null && object.equals(currentNode.item)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void linkBefore(T value, Node<T> place) {
        final Node<T> prev = place.prev;
        final Node<T> newNode = new Node<>(prev, value, place);
        place.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index entered. Index: "
                    + index + " list size: " + size);
        }
    }

    private Node<T> searchNodeByIndex(int index) {
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
