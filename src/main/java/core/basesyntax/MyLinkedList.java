package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> header;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            header = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, header);
            header.prev = newNode;
            header = newNode;
        } else {
            Node<T> currentPosition = findNode(index);
            Node<T> newNode = new Node<>(currentPosition.prev, value, currentPosition);
            currentPosition.prev.next = newNode;
            currentPosition.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List is null");
        }
        for (T items : list) {
            add(items);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = findNode(index);
        T removedValue = currentNode.value;
        unlink(currentNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = header;
        for (int i = 0; i < size; i++) {
            if (object == removedNode.value || object != null
                       && object.equals(removedNode.value)) {
                unlink(removedNode);
                return true;
            }
            removedNode = removedNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = header;
            for (int i = 0; i != index; i++) {
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

    private void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> previous = node.prev;
        if (previous == null) {
            header = next;
        } else {
            previous.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
            node.next = null;
        }
        node.value = null;
        size--;
    }
}
