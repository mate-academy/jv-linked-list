package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<E> {
        private Node<E> next;
        private Node<E> prev;
        private E element;

        Node(Node<E> prev, E element,Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        addToEnd(value);
    }

    @Override
    public void add(T value, int index) {
        checkElementPosition(index);
        if (index == size) {
            addToEnd(value);
        } else if (index == 0) {
            addAtTheFront(value);
        } else {
            addToMiddle(value,getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementExistence(index);
        Node<T> lastListNode = getNodeByIndex(index);
        for (int i = size - 1; i > index; i--) {
            lastListNode = getNodeByIndex(i).prev;
        }
        return lastListNode.element;
    }

    @Override
    public T set(T value, int index) {
        checkElementExistence(index);
        Node<T> nodeToBeReplaced = getNodeByIndex(index);
        T replacedNodeReturn = nodeToBeReplaced.element;
        nodeToBeReplaced.element = value;
        return replacedNodeReturn;
    }

    @Override
    public T remove(int index) {
        checkElementExistence(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> toBeRemoved = head;
        while (toBeRemoved != null) {
            if (Objects.equals(toBeRemoved.element, object)) {
                unlink(toBeRemoved);
                return true;
            }
            toBeRemoved = toBeRemoved.next;
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

    private void checkElementPosition(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Element " + index
                    + " is absent " + size);
        }
    }

    private void checkElementExistence(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds " + size);
        }
    }

    private void addAtTheFront(T value) {
        Node<T> newNode = new Node<>(null,value,null);
        newNode.next = head;
        newNode.prev = null;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        size++;
    }

    private void addToMiddle(T value, Node<T> currentNode) {
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    private void addToEnd(T value) {
        Node<T> lastListNode = tail;
        Node<T> newNode = new Node<>(tail,value,null);
        tail = newNode;
        if (lastListNode == null) {
            head = newNode;
        } else {
            lastListNode.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkElementExistence(index);
        Node<T> lastNode = tail;
        int i = size - 1;
        while (i > index) {
            lastNode = lastNode.prev;
            i--;
        }
        return lastNode;
    }

    private T unlink(Node<T> nodeToBeUnlinked) {
        Node<T> previousNode = nodeToBeUnlinked.prev;
        Node<T> nextNode = nodeToBeUnlinked.next;
        if (nodeToBeUnlinked.prev == null) {
            head = nextNode;
        } else {
            nodeToBeUnlinked.prev.next = nextNode;
            nodeToBeUnlinked.prev = null;
        }
        if (nodeToBeUnlinked.next == null) {
            tail = previousNode;
        } else {
            nodeToBeUnlinked.next.prev = previousNode;
            nodeToBeUnlinked.next = null;
        }
        T elementToBeRemoved = nodeToBeUnlinked.element;
        size--;
        return elementToBeRemoved;
    }
}
