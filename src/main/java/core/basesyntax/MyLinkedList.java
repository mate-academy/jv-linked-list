package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            // this is different if than in "validateIndex" --> (index < 0 || index >= size)!!!!!
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> currentNode = gettNodeByIndex(index);
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {

        return gettNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = gettNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = gettNodeByIndex(index);
        unlink(currentNode);

        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = gettNodeByValue(object);
        if (currentNode != null) {
            unlink(currentNode);
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

    private Node<T> gettNodeByIndex(int index) {
        validateIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> gettNodeByValue(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == value || value != null && value.equals(currentNode.value)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode == null) {
            return;
        }

        Node<T> prev = currentNode.prev;
        Node<T> next = currentNode.next;

        head = (prev == null) ? next : head;
        tail = (next == null) ? prev : tail;

        if (prev != null) {
            prev.next = next;
            currentNode.prev = null;
        }

        if (next != null) {
            next.prev = prev;
            currentNode.next = null;
        }
        size--;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
    }

    static class Node<T> {

        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

}
