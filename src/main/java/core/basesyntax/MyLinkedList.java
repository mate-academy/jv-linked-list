package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value);
        } else {
            insertAfter(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> currentNode;
        if (index == 0) {
            if (head == null) {
                addFirst(value);
            } else {
                insertBefore(value);
            }
        } else if (index == size) {
            insertAfter(value);
        } else if (index == (size - 1)) {
            checkIndex(index);
            Node<T> newNode = new Node<>(tail.prev, value, tail);
            tail.prev.next = newNode;
            tail.prev = newNode;
        } else {
            currentNode = searchNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            newNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> lists) {
        for (T list : lists) {
            add(list);
        }
    }

    @Override
    public T get(int index) {
        return searchNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode;
        T e;
        currentNode = searchNodeByIndex(index);
        e = currentNode.item;
        currentNode.item = value;
        return e;
    }

    @Override
    public T remove(int index) {
        T deleteElement;
        checkIndex(index);
        if (index == 0) {
            deleteElement = unlinkFirst();
        } else if (index == size - 1) {
            deleteElement = unlinkLast();
        } else {
            deleteElement = unlink(searchNodeByIndex(index));
        }
        return deleteElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = searchNodeByElement(object);
        if (currentNode == null) {
            return false;
        } else if (currentNode.equals(head)) {
            unlinkFirst();
            return true;
        } else if (currentNode.equals(tail)) {
            unlinkLast();
            return true;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        head = newNode;
        tail = head;
    }

    private void insertBefore(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
    }

    private void insertAfter(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
    }

    private T unlink(Node<T> object) {
        object.prev.next = object.next;
        object.next.prev = object.prev;
        size--;
        return object.item;
    }

    private T unlinkFirst() {
        T deleteElement;
        if (size == 1) {
            deleteElement = head.item;
            tail = null;
            head = null;
        } else {
            deleteElement = head.item;
            head = head.next;
            head.prev = null;
        }
        size--;
        return deleteElement;
    }

    private T unlinkLast() {
        T deleteElement;
        deleteElement = tail.item;
        tail = tail.prev;
        tail.next = null;
        size--;
        return deleteElement;
    }

    private Node<T> searchNodeByElement(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(currentNode.item, object)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private Node<T> searchNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " don`t exist, size = "
                    + size);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
