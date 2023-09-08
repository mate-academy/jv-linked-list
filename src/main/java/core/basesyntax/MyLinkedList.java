package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        
    }

    @Override
    public void add(T value) {
        if (head != null) {
            Node<T> node = getElementByIndex(size - 1);
            Node<T> newNode = new Node<>(node, value, null);
            node.next = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            tail = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> node = getElementByIndex(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            if (node.prev != null) {
                node.prev.next = newNode;
            } else {
                head = newNode;
            }
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getElementByIndex(index).value;
        getElementByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getElementByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value.equals(object)) {
                unlink(node);
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index less then 0 or more size!");
        }
    }

    private Node<T> getElementByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
