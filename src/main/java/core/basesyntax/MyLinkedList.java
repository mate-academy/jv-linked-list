package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> currentNode = new Node<>(value);
        if (size == 0) {
            head = currentNode;
            tail = head;
        } else {
            currentNode.prev = tail;
            tail.next = currentNode;
            tail = currentNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        isIndexExist(index);
        Node<T> currentNode = findByIndex(index);
        Node<T> node = new Node<>(value);
        if (head == currentNode) {
            head = node;
        } else {
            currentNode.prev.next = node;
        }
        node.prev = currentNode.prev;
        node.next = currentNode;
        currentNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return checkNode(findByIndex(index)).item;
    }

    @Override
    public boolean remove(T object) {
        return checkNode(findByObject(object)) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void isIndexExist(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " dosen't exist");
        }
    }

    public Node<T> findByIndex(int index) {
        isIndexExist(index);
        if (head == null) {
            return null;
        }
        Node<T> currentNode = head;
        int i = 0;
        while (i != index) {
            currentNode = currentNode.next;
            i++;
        }
        return currentNode;
    }

    private Node<T> findByObject(T object) {
        Node<T> element = head;
        do {
            T value = element.item;
            if (value == object || value != null && value.equals(object)) {
                return element;
            }
            element = element.next;
        } while (element.next != null);
        return null;
    }

    private Node<T> checkNode(Node<T> node) {
        if (node == null) {
            return null;
        }
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
        return node;
    }

    private static class Node<T> {

        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(T value) {
            this.item = value;
        }
    }
}
