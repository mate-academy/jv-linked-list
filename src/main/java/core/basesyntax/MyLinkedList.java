package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> positionNode = getNode(index);
        Node<T> previous = positionNode.prev;
        Node<T> newNode = new Node<>(previous, value, positionNode);
        positionNode.prev = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> positionNode = getNode(index);
        T previousValue = positionNode.value;
        positionNode.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node<T> result = getNode(index);
        unlink(getNode(index));
        return result.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> positionNode = head;
        while (positionNode != null) {
            if ((positionNode.value == object)
                    || (positionNode.value != null && positionNode.value.equals(object))) {
                unlink(positionNode);
                return true;
            }
            positionNode = positionNode.next;
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> result;
        if (index < (size / 2)) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " is out of bound.");
        }
    }

    private void unlink(Node<T> node) {
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        }
        if (node == head) {
            head = node.next;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
