package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<>(prevNode, value, null);
        tail = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, tail);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> nextNode = findElementByIndex(index);
            Node<T> prevNode = nextNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = newNode;
            prevNode.next = newNode;
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
        return findElementByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> myNode = findElementByIndex(index);
        T result = myNode.item;
        myNode.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        return unlink(findElementByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if (Objects.equals(object, currentNode.item)) {
                unlink(currentNode);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Wrong index, " + size);
        }
    }

    private T unlink(Node<T> newNode) {
        final T result = newNode.item;
        Node<T> prev = newNode.prev;
        Node<T> next = newNode.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        newNode.item = null;
        size--;
        return result;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> findElementByIndex(int index) {
        checkIndex(index);
        if (index < size / 2) {
            Node<T> newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        } else {
            Node<T> newNode = tail;
            for (int i = 0; i < size - index - 1; i++) {
                newNode = newNode.prev;
            }
            return newNode;
        }
    }
}
