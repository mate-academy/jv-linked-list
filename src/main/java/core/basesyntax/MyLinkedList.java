package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
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
            newNode.prev.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> currentNode = null;
        if (index == 0) {
            if (head == null) {
                add(value);
            }
            currentNode = new Node<>(null, value, head);
            head.prev = currentNode;
            head = currentNode;
            size++;
            return;
        } else if (index == size) {
            currentNode = new Node<>(tail.prev, value, tail);
            tail.prev.next = currentNode;
            tail.prev = currentNode;
            size++;
            return;
        }
        if (checkIndex(index)) {
            currentNode = searchElement(index);
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
        Node<T> currentNode = null;
        if (checkIndex(index)) {
            currentNode = searchElement(index);
        }
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> sameNode = null;
        T object = null;
        if (checkIndex(index)) {
            sameNode = searchElement(index);
            object = sameNode.item;
            sameNode.item = value;
        }
        return object;
    }

    @Override
    public T remove(int index) {
        Node<T> delNode = null;
        if (checkIndex(index)) {
            if (index == 0) {
                delNode.item = unlinkFirst();
                return delNode.item;
            } else if (index == (size - 1)) {
                delNode.item = unlinkLast();
                return delNode.item;
            } else {
                delNode = searchElement(index);
                unlink(delNode);
            }
        }
        return delNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = searchNode(object);
        if (currentNode == null) {
            return false;
        }
        if (currentNode.equals(head)) {
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

    public void unlink(Node<T> object) {
        Node<T> currentNode = object;
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        size--;
    }

    public Node<T> unlinkFirst() {
        Node<T> object = null;
        if (size == 1) {
            object = head;
            tail = null;
            head = null;
            size--;
            return object;
        }
        object = head.item;
        head = head.next;
        head.prev = null;
        size--;
        return object;
    }

    public T unlinkLast() {
        Node<T> delNode = null;
        delNode.item = tail.item;
        tail = tail.prev;
        tail.next = null;
        size--;
        return delNode.item;
    }

    public Node<T> searchNode(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(currentNode.item, object)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    public Node<T> searchElement(int index) {
        int count = 0;
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (index == count) {
                break;
            }
            count++;
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public Node<T> lastNode() {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.next == null) {
                break;
            }
        }
        return currentNode;
    }

    public boolean checkIndex(int index) {
        if (index < 0 || index > (size - 1)) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " don`t exist");
        } else {
            return true;
        }
    }
}
