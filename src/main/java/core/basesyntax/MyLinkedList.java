package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> currentNode = getNodeAtIndex(index);
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null && !list.isEmpty()) {
            for (T element : list) {
                Node<T> newNode = new Node<>(tail, element, null);
                if (tail != null) {
                    tail.next = newNode;
                }
                tail = newNode;
                if (size == 0) {
                    head = newNode;
                }
                size++;
            }
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        Node<T> currentNode = getNodeAtIndex(index);
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> currentNode = getNodeAtIndex(index);
        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> removedNode = getNodeAtIndex(index);
        removeNode(removedNode);
        return removedNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((object == null && currentNode.element == null)
                    || (object != null && object.equals(currentNode.element))) {
                removeNode(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    private boolean validIndex(int index) {
        return index >= 0 && index < size;
    }

    private void validateIndex(int index) {
        if (!validIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void removeNode(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
