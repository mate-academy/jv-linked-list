package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    private class Node<T> {
        private T currentElement;
        private Node<T> prevElement;
        private Node<T> nextElement;

        private Node(Node<T> prevElement, T currentElement, Node<T> nextElement) {
            this.prevElement = prevElement;
            this.currentElement = currentElement;
            this.nextElement = nextElement;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            firstNode = newNode;
            lastNode = newNode;
        } else {
            lastNode.nextElement = newNode;
            newNode.prevElement = lastNode;
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkValidationIndex(index, size);
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = findByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prevElement, value, currentNode);
            newNode.nextElement.prevElement = newNode;
            if (index != 0) {
                newNode.prevElement.nextElement = newNode;
            } else {
                firstNode = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T current : list) {
            add(current);
        }
    }

    @Override
    public T get(int index) {
        checkValidationIndex(index, size - 1);
        Node<T> currentNode = findByIndex(index);
        return currentNode.currentElement;
    }

    @Override
    public T set(T value, int index) {
        checkValidationIndex(index, size - 1);
        Node<T> currentNode = findByIndex(index);
        T deletedValue = currentNode.currentElement;
        currentNode.currentElement = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        checkValidationIndex(index, size - 1);
        Node<T> currentNode = findByIndex(index);
        unlink(currentNode);
        return currentNode.currentElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            if (currentNode.currentElement == null && object == null
                    || currentNode != null && currentNode.currentElement.equals(object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.nextElement;
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

    private Node<T> findByIndex(int index) {
        checkValidationIndex(index, size - 1);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = firstNode;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.nextElement;
            }
        } else {
            currentNode = lastNode;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prevElement;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> currentNode) {
        if (size == 1 && currentNode == firstNode) {
            firstNode = null;
            lastNode = null;
            size--;
            return;
        }
        if (currentNode == firstNode) {
            firstNode = currentNode.nextElement;
            currentNode.nextElement.prevElement = null;
            size--;
            return;
        }
        if (currentNode == lastNode) {
            lastNode = currentNode.prevElement;
            currentNode.prevElement.nextElement = null;
            size--;
            return;
        }
        currentNode.prevElement.nextElement = currentNode.nextElement;
        currentNode.nextElement.prevElement = currentNode.prevElement;
        size--;
    }

    private void checkValidationIndex(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }
}
