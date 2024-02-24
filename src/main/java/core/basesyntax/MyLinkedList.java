package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            Node<T> newNode = new Node<T>(null, value, null);
            head = newNode;
            tail = head;
        } else {
            Node<T> newNode = new Node<T>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> currentNode;
        if (index == 0) {
            if (head == null) {
                Node<T> newNode = new Node<T>(null, value, null);
                head = newNode;
                tail = head;
            } else {
                currentNode = new Node<>(null, value, head);
                head.prev = currentNode;
                head = currentNode;
            }
        } else if (index == size) {
            add(value);
            return;
        } else if (index == (size - 1)) {
            if (checkIndex(index)) {
                currentNode = new Node<>(tail.prev, value, tail);
                tail.prev.next = currentNode;
                tail.prev = currentNode;
            }
        } else {
            currentNode = searchNodeByIndex(index);
            Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);
            newNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return searchNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode;
        T e = null;
        currentNode = searchNodeByIndex(index);
        e = currentNode.item;
        currentNode.item = value;
        return e;
    }

    @Override
    public T remove(int index) {
        T deleteElement = null;
        if (checkIndex(index)) {
            deleteElement = (index == 0) ? unlinkFirst()
                    : (index == size - 1) ? unlinkLast()
                    : unlink(searchNodeByIndex(index));
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
        }
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
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

    public T unlink(Node<T> object) {
        Node<T> currentNode = object;
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        size--;
        return currentNode.item;
    }

    public T unlinkFirst() {
        T deleteElement = null;
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

    public T unlinkLast() {
        T deleteElement = null;
        deleteElement = tail.item;
        tail = tail.prev;
        tail.next = null;
        size--;
        return deleteElement;
    }

    public Node<T> searchNodeByElement(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(currentNode.item, object)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    public Node<T> searchNodeByIndex(int index) {
        if (checkIndex(index)) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        return null;
    }

    public boolean checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " don`t exist");
        }
        return true;
    }
}
