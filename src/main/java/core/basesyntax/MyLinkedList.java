package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static int linkedListSize;
    Node<T> head;
    Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, null, value);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        linkedListSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > linkedListSize) {
            throw new IndexOutOfBoundsException("Index out of bounds! {void add}");
        }
        if (index == 0) {
            add(value);
            return;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.next;
        }
        Node<T> newNode = new Node<>(currentNode, currentNode.next, value);
        currentNode.next.prev = newNode;
        currentNode.next = newNode;
        linkedListSize++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List can't be null! {void addAll}");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > linkedListSize) {
            throw new IndexOutOfBoundsException("Index of bounds! {T get}");
        }
        if (index < (linkedListSize >> 1)) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.value;
        } else {
            Node<T> currentNode = tail;
            for (int i = linkedListSize - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode.value;
        }
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index > linkedListSize) {
            throw new IndexOutOfBoundsException("Index of bounds! {T set}");
        }
        if (index < (linkedListSize >> 1)) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.value = value;
        } else {
            Node<T> currentNode = tail;
            for (int i = linkedListSize - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode.value = value;
        }
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return linkedListSize;
    }

    @Override
    public boolean isEmpty() {
        return linkedListSize == 0;
    }

    static class Node<T> {
        Node<T> prev;
        Node<T> next;
        T value;

        public Node(Node<T> prev, Node<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
