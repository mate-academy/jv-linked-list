package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkBounds(index);
        Node<T> next = findNode(index);
        Node<T> prior = next.prior;
        Node<T> newNode = new Node<>(prior, value, next);
        next.prior = newNode;
        if (prior != null) {
            prior.next = newNode;
        } else {
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
        T valueToRemove = currentNode.value;
        currentNode.value = value;
        return valueToRemove;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (areEqual(node.value, object)) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    private Node<T> findNode(int index) {
        checkBounds(index);
        Node<T> node;
        if ((size / 2) > index) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size; i > (index + 1); i--) {
                node = node.prior;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            if (size != 1) {
                head.next.prior = null;
                head = head.next;
            } else {
                head = null;
                tail = null;
            }
        } else if (node == tail) {
            tail.prior.next = null;
            tail = tail.prior;
        } else {
            node.prior.next = node.next;
            node.next.prior = node.prior;
        }
        size--;
    }

    private boolean areEqual(T first, T second) {
        return first == second || first != null && first.equals(second);
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prior;
        private T value;

        Node(Node<T> prior, T value, Node<T> next) {
            this.prior = prior;
            this.value = value;
            this.next = next;
        }
    }
}
