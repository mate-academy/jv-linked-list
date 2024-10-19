package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> currentNode;
    private Node<T> previousNode;
    private Node<T> nextNode;
    private int size;

    public static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newElement = new Node<T>(null, value, null);
            head = newElement;
            tail = newElement;
        } else {
            Node<T> newElement = new Node<T>(tail, value, null);
            Node<T> previousNode = tail;
            previousNode.next = newElement;
            tail = newElement;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index passed must be greater or equal to 0 "
                    + "and less or equal to" + size);
        }
        if (size - index == 0 || size == 0) {
            add(value);
            return;
        }
        iterateCurrentNode(index);
        addNodeInTheMiddle(value);
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        verifyIndex(index);
        iterateCurrentNode(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("The list is empty");
        }
        verifyIndex(index);
        iterateCurrentNode(index);
        Node<T> returnItem = new Node<>(currentNode, currentNode.item, currentNode.next);
        currentNode.item = value;
        return returnItem.item;
    }

    @Override
    public T remove(int index) {
        verifyIndex(index);
        if (index == 0) {
            return removeFirstNode();
        }
        iterateCurrentNode(index);
        assignPreviousAndNextNode();
        unlink(currentNode);
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            throw new NullPointerException("The list is empty");
        }
        if (head.item == object || head.item != null && head.item.equals(object)) {
            return removeFromTheHead();
        }
        findInTheMiddle(object);
        if (currentNode == null) {
            return false;
        }
        assignPreviousAndNextNode();
        unlink(currentNode);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void iterateCurrentNode(int index) {
        currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
    }

    private void verifyIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("The index passed must be equal "
                    + "or greater than 0 and less than " + size);
        }
    }

    private void addNodeInTheMiddle(T value) {
        previousNode = currentNode.prev;
        Node<T> newElement = new Node<>(previousNode, value, currentNode);
        if (currentNode == head || currentNode != null && currentNode.equals(head)) {
            head = newElement;
        }
        if (previousNode != null) {
            previousNode.next = newElement;
        }
        currentNode.prev = newElement;
        size++;
    }

    private T removeFirstNode() {
        final Node<T> removedElement = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        size--;
        return removedElement.item;
    }

    private void assignPreviousAndNextNode() {
        previousNode = currentNode.prev;
        nextNode = currentNode.next;
        if (nextNode != null) {
            nextNode.prev = previousNode;
        }
        if (previousNode != null) {
            previousNode.next = nextNode;
        }
    }

    private void unlink(Node<T> node) {
        node.prev = null;
        node.next = null;
    }

    private void findInTheMiddle(T object) {
        currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object || currentNode.item != null
                    && currentNode.item.equals(object)) {
                break;
            }
            currentNode = currentNode.next;
        }
    }

    private boolean removeFromTheHead() {
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        size--;
        return true;
    }
}
