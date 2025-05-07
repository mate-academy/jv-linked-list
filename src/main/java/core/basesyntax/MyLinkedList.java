package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INCORRECT_INDEX = "Incorrect index: ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INCORRECT_INDEX + index);
        }
        if (size == index) {
            add(value);
            return;
        }
        if (index == 0) {
            insertFirst(value);
            return;
        }
        Node<T> nextNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(nextNode.prev, value, nextNode);
        nextNode.prev.next = newNode;
        nextNode.prev = newNode;
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
        validateIndex(index);
        return (T) getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> removedNode = getNodeByIndex(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = getNodeByValue(object);
        if (removedNode != null) {
            unlink(removedNode);
            return true;
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INCORRECT_INDEX + index);
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
            if (node == head) {
                head = node.next;
            }
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
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

    private void insertFirst(T value) {
        if (head == null) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == value
                    || (value != null && value.equals(currentNode.value))) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }
}
