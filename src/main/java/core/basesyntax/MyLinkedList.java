package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> lastElement = tail;
        Node<T> newNode = new Node<>(lastElement, value, null);
        tail = newNode;
        if (lastElement == null) {
            head = newNode;
        } else {
            lastElement.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addAtFirstPosition(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds. List size: %d", index, size)
            );
        }
        addBefore(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = findByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object
                    || (currentNode.value != null
                    && currentNode.value.equals(object))) {
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

    private Node<T> findByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds. List size: %d", index, size)
            );
        }
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size; i > (index + 1); i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (node.next == null) {
            if (node.previous != null) {
                node.previous.next = null;
            }
            tail = node.previous;
        } else if (node.previous == null) {
            node.next.previous = null;
            head = node.next;
        } else {
            node.next.previous = node.previous;
            node.previous.next = node.next;
        }
        size--;
    }

    private void addAtFirstPosition(T value) {
        Node<T> firstElement = head;
        Node<T> newNode = new Node<>(null, value, firstElement);
        head = newNode;
        if (firstElement == null) {
            tail = newNode;
        } else {
            firstElement.previous = newNode;
        }
        size++;
    }

    private void addBefore(T value, int index) {
        Node<T> next = findByIndex(index);
        Node<T> prev = next.previous;
        Node<T> newNode = new Node<>(prev, value, next);
        next.previous = newNode;
        if (prev != null) {
            prev.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
