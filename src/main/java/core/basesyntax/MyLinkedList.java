package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
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
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T values : list) {
            add(values);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;

    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNode(index);
        T returnedValue = newNode.value;
        newNode.value = value;
        return returnedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedValue = getNode(index);
        return unlink(removedValue);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object || (object != null
                    && object.equals(current.value))) {
                unlink(current);
                return true;
            }
            current = current.next;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bound exception");
        }
    }

    private Node<T> getNode(int index) {
        if (index < size / 2) {
            Node<T> newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        } else {
            Node<T> newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
            return newNode;
        }
    }

    private T unlink(Node<T> removedValue) {
        Node<T> nextNode = removedValue.next;
        Node<T> prevNode = removedValue.prev;
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            removedValue.next = null;
        }
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            removedValue.prev = null;
        }
        T tempValue = removedValue.value;
        removedValue.value = null;
        size--;
        return tempValue;
    }
}
