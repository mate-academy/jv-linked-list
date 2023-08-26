package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (isEmpty()) {
            head = tail = new Node<>(null, value, null);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> currentNode = findNodeByIndex(index - 1);
            Node<T> newNode = new Node<>(currentNode, value, currentNode.next);
            currentNode.next.prev = newNode;
            currentNode.next = newNode;
        }
        size++;
    }

    @Override
    public T set(T value, int index) {
        isIndexValid(index);
        Node<T> nodeToUpdate = findNodeByIndex(index);
        T oldValue = nodeToUpdate.element;
        nodeToUpdate.element = value;
        return oldValue;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        isIndexValid(index);
        Node<T> foundNode = findNodeByIndex(index);
        return foundNode.element;
    }

    @Override
    public T remove(int index) {
        isIndexValid(index);
        Node<T> nodeToRemove;
        if (index == 0) {
            nodeToRemove = head;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else if (index == size - 1) {
            nodeToRemove = tail;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            nodeToRemove = findNodeByIndex(index);
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
        }
        size--;
        return nodeToRemove.element;
    }


    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        int counter = 0;
        while (currentNode != null) {
            if (currentNode.element == object || (currentNode.element != null && currentNode.element.equals(object))) {
                remove(counter);
                return true;
            }
            currentNode = currentNode.next;
            counter++;
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private boolean isIndexValid(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return true;
    }
}
