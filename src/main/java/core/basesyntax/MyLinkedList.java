package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }
        if (head.equals(tail)) {
            head.next = tail;
        }
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> existingNode = search(index);
        Node<T> newNode = new Node<>(existingNode.prev, value, existingNode);
        size++;
        if (existingNode.equals(head)) {
            existingNode.prev = newNode;
            head = newNode;
            return;
        }
        existingNode.prev.next = newNode;
        existingNode.prev = newNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return search(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> existingNode = search(index);
        T replacedValue = existingNode.value;
        existingNode.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = search(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = searchByObject(object);
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> search(int index) {
        validateIndex(index);
        if ((size / 2) > index) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        Node<T> currentNode = tail;
        for (int i = 0; i < size - index - 1; i++) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private Node<T> searchByObject(T value) {
        Node<T> checkedNode = head;
        for (int i = 0; i < size; i++) {
            if (checkedNode.value != null && checkedNode.value.equals(value)
                    || checkedNode.value == value) {
                return checkedNode;
            }
            checkedNode = checkedNode.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        size--;
        if (node.equals(head)) {
            head = head.next;
            head.prev = null;
            return;
        }
        if (node.equals(tail)) {
            tail = tail.prev;
            tail.next = null;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
