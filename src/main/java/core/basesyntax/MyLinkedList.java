package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> node = new Node<T>(null,value,null);
            head = node;
            tail = node;
            size++;
            return true;
        }
        addToTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of Bound");
        }
        if (index == 0) {
            addToHead(value);
            return;
        } else if (index == size) {
            addToTail(value);
            return;
        }
        Node<T> currentNode = findingElement(index);
        Node<T> node = new Node(currentNode.previous, value, currentNode);
        currentNode.previous.next = node;
        currentNode.previous = node;
        size++;
    }

    public void addToHead(T value) {
        if (head == null) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
            size++;
            return;
        }
        Node<T> currentNode = head;
        Node<T> node = new Node<>(null, value, currentNode);
        currentNode.previous = node;
        head = node;
        size++;
    }

    public void addToTail(T value) {
        Node<T> previousNode = tail;
        Node<T> node = new Node<T>(previousNode, value, null);
        previousNode.next = node;
        tail = node;
        size++;
    }

    private Node<T> findingElement(int index) {
        if (size / 2 >= index) {
            return searchFromHead(index);
        } else {
            return searchFromTail(index);
        }
    }

    private Node<T> searchFromTail(int index) {
        Node<T> currentNode = tail;
        int localSize = (size - 1) - index;
        while (localSize > 0) {
            currentNode = currentNode.previous;
            localSize--;
        }
        return currentNode;
    }

    private Node<T> searchFromHead(int index) {
        Node<T> currentNode = head;
        int localSize = 0;
        while (localSize != index) {
            currentNode = currentNode.next;
            localSize++;
        }
        return currentNode;
    }

    public void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index out of Bound");
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0;i < list.size();i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findingElement(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        if (index == 0) {
            T returnedValue = head.item;
            head.item = value;
            return returnedValue;
        }
        Node<T> currentNode = findingElement(index);
        T valueForReturn = currentNode.item;
        currentNode.item = value;
        return valueForReturn;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = findingElement(index);
        unlink(currentNode);
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == null
                    || currentNode.item != null && currentNode.item.equals(object)) {
                unlink(currentNode);
                size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.previous;
        if (previousNode == null && nextNode != null) {
            head = nextNode;
            nextNode.previous = null;
        }
        if (nextNode == null && previousNode != null) {
            tail = previousNode;
            previousNode.next = null;
        }
        if (nextNode != null && previousNode != null) {
            previousNode.next = nextNode;
            nextNode.previous = previousNode;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
