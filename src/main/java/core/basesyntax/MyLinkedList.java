package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> currentNode = new Node<>(tail, value, null);
        if (size < 1) {
            head = currentNode;
        } else {
            tail.next = currentNode;
        }
        tail = currentNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        Node<T> currentNode = findNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
        if (index == 0) {
            head = newNode;
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
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNode(index);
        T currentValue = currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNode(index);
        unlink(currentNode.prev, currentNode.next, index);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, currentNode.value)) {
                unlink(currentNode.prev, currentNode.next, i);
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode;
        checkIndex(index);
        if (size / 2 > index) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is incorrect " + index);
        }
    }

    private void unlink(Node<T> prev, Node<T> next, int index) {
        if (index == 0) {
            head = next;
        } else if (index == size - 1) {
            tail = prev;
        } else {
            prev.next = next;
            next.prev = prev;
        }
        size--;
    }
}
