package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size = 0;

    public MyLinkedList() {
        firstNode = null;
        lastNode = null;
    }

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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
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
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> currentNode = findByIndex(index);
        return currentNode.currentElement;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> currentNode = findByIndex(index);
        T deletedValue = currentNode.currentElement;
        currentNode.currentElement = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> currentNode = findByIndex(index);
        deleteLinks(currentNode);
        return currentNode.currentElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            if (currentNode.currentElement == null && object == null
                    || currentNode != null && currentNode.currentElement.equals(object)) {
                deleteLinks(currentNode);
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
        int counter = 0;
        Node<T> currentNode = firstNode;
        while (currentNode.nextElement != null) {
            if (counter == index) {
                break;
            }
            currentNode = currentNode.nextElement;
            counter++;
        }
        return currentNode;
    }

    private void deleteLinks(Node<T> currentNode) {
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
}
