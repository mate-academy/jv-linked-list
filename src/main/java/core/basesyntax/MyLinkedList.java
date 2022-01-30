package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = tail = new Node<>(value, null, null);
        } else {
            Node<T> newNode = new Node<>(value, null, tail);
            tail.next = newNode;
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
        Node<T> newNode = new Node<>(value, null, null);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.previous = tail;
            tail = tail.next;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            currentNode.previous.next = newNode;
            newNode.previous = currentNode.previous;
            newNode.next = currentNode;
            currentNode.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
        T valueToReturn = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return valueToReturn;

    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
        T removedNodeValue = getNodeByIndex(index).value;
        if (index == 0) {
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.previous = head.previous.previous;
            }
        } else if (index == size - 1) {
            tail.previous.next = tail.next;
            tail = tail.previous;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            currentNode.previous.next = currentNode.next;
            currentNode.next.previous = currentNode.previous;
        }
        size--;
        return removedNodeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object || currentNode.value != null
                    && currentNode.value.equals(object)) {
                remove(i);
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
}
