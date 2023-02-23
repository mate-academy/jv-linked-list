package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node<T> prev, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (size == 0) {
            tail = head = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            Node<T> node = new Node<>(value);
            insertNode(findNode(index), node);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNode(index);
        T element = currentNode.element;
        currentNode.element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        return deleteReferences(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (isEqualsElement(currentNode.element, object)) {
                deleteReferences(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private boolean isEqualsElement(T element1, T element2) {
        return (element1 == element2) || (element1 != null && element1.equals(element2));
    }

    private void insertNode(Node node, Node<T> newNode) {
        newNode.next = node;
        newNode.prev = node.prev;
        if (head == node) {
            head = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    private Node<T> findNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid input " + index);
        }
        return index >= size / 2 ? findElementFromTail(index) : findElementFromHead(index);
    }

    private Node<T> findElementFromHead(int index) {
        int currentIndex = 0;
        Node<T> currentNode = head;
        while (currentIndex++ != index) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> findElementFromTail(int index) {
        int currentIndex = size - 1;
        Node<T> currentNode = tail;
        while (currentIndex-- != index) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private T deleteReferences(Node<T> currentNode) {
        if (currentNode == head) {
            head = currentNode.next;
        }
        if (currentNode == tail) {
            tail = currentNode.prev;
        }
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode.element;
    }
}
