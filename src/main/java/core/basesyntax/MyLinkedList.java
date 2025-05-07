package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> previousNode;
    private Node<T> nextNode;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newElement = new Node<T>(null, value, null);
            head = newElement;
            tail = newElement;
        } else {
            Node<T> newElement = new Node<T>(tail, value, null);
            newElement.prev = tail;
            Node<T> previousNode = tail;
            previousNode.next = newElement;
            tail = newElement;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        verifyIndexWhenAddingANode(index);
        if (size - index == 0 || size == 0) {
            add(value);
            return;
        }
        addNodeInTheMiddle(value, iterateCurrentNode(index));
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
        return iterateCurrentNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("The list is empty");
        }
        verifyIndex(index);
        T returnItem = iterateCurrentNode(index).item;
        iterateCurrentNode(index).item = value;
        return returnItem;
    }

    @Override
    public T remove(int index) {
        verifyIndex(index);
        if (index == 0) {
            return removeFirstNode();
        }
        T removedItem = iterateCurrentNode(index).item;
        assignPreviousAndNextNode(iterateCurrentNode(index));
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            throw new NullPointerException("The list is empty");
        }
        if (object == null ? head.item == null : object.equals(head.item)) {
            return removeFromTheHead();
        }
        if (findInTheMiddle(object) == null) {
            return false;
        }
        assignPreviousAndNextNode(findInTheMiddle(object));
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

    private Node<T> iterateCurrentNode(int index) {
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void verifyIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("The index passed must be equal "
                    + "or greater than 0 and less than " + size);
        }
    }

    private void verifyIndexWhenAddingANode(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index passed must be greater or equal to 0 "
                    + "and less or equal to" + size);
        }
    }

    private void addNodeInTheMiddle(T value, Node<T> currentNode) {
        previousNode = currentNode.prev;
        Node<T> newElement = new Node<>(previousNode, value, currentNode);
        if (currentNode == head) {
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

    private void assignPreviousAndNextNode(Node<T> currentNode) {
        previousNode = currentNode.prev;
        nextNode = currentNode.next;
        unlink(currentNode);
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
        if (tail.equals(node)) {
            Node<T> newNode = tail;
            newNode.next = null;
            newNode.prev = null;
        }
        if (head.equals(node)) {
            Node<T> newNode = head;
            newNode.next = null;
            newNode.prev = null;
        }
    }

    private Node<T> findInTheMiddle(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object || currentNode.item != null
                    && currentNode.item.equals(object)) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private boolean removeFromTheHead() {
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        size--;
        return true;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
