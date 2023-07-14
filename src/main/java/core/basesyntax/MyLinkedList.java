package core.basesyntax;

import core.basesyntax.service.ObjectService;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) {
            head = node;
        }
        if (tail != null) {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if ((index == 0 && size == 0) || size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> foundedNode = getNodeByIndex(index);
        if (foundedNode != null) {
            insertNewNode(value, foundedNode);
        } else {
            add(value);
        }
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
        checkIndex(index);
        Node<T> foundedNode = getNodeByIndex(index);
        return foundedNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> foundedNode = getNodeByIndex(index);
        T oldItem = foundedNode.item;
        foundedNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> foundedNode = getNodeByIndex(index);
        unlink(foundedNode);
        return foundedNode.item;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> foundedNode = getNodeByValue(object);
        if (foundedNode != null) {
            unlink(foundedNode);
            return true;
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
            throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            return searchFromHead(index);
        } else {
            return searchFromTail(index);
        }
    }

    private Node<T> searchFromHead(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> currentNode = head;
        while (!ObjectService.equals(currentNode.item, value)) {
            if (currentNode.next == null) {
                return null;
            }
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }

    private void insertNewNode(T value, Node<T> oldNode) {
        Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
        if (oldNode.prev != null) {
            oldNode.prev.next = newNode;
        } else {
            head = newNode;
        }
        oldNode.prev = newNode;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
