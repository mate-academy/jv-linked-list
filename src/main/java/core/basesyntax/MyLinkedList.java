package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> prevNode = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev = newNode;
            newNode.prev = prevNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T changedElement = currentNode.element;
        currentNode.element = value;
        return changedElement;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeByIndex(index);
        T value = removedNode.element;
        unlink(removedNode);
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.element != null && currentNode.element.equals(object)
                    || currentNode.element == object) {
                unlink(currentNode);
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

    private Node<T> getNodeByIndex(int index) {
        checkIndexRange(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndexRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of list bound");
        }
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode != null && nextNode != null) {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        } else if (prevNode == null && nextNode != null) {
            nextNode.prev = prevNode;
            head = nextNode;
        } else if (prevNode != null && nextNode == null) {
            prevNode.next = nextNode;
            tail = prevNode;
        }
        node.prev = node.next = null;
        size--;
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }
}
