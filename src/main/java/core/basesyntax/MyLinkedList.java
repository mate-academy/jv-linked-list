package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = head;
            if (head != null) head.prev = newNode;
            head = newNode;
            if (size == 0) tail = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            if (tail != null) tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> nextNode = findNode(index);
            Node<T> prevNode = nextNode.prev;
            newNode.next = nextNode;
            newNode.prev = prevNode;
            if (prevNode != null) prevNode.next = newNode;
            nextNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = findNode(index);
        T removedValue = nodeToRemove.value;
        unlink(nodeToRemove);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if ((object == null && currentNode.value == null) || (object != null && object.equals(currentNode.value))) {
                unlink(currentNode);
                return true;
            }
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

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> currentNode = (index < size / 2) ? head : tail;
        int step = (index < size / 2) ? 1 : -1;
        for (int i = (index < size / 2) ? 0 : size - 1; i != index; i += step) {
            currentNode = (step == 1) ? currentNode.next : currentNode.prev;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) node.prev.next = node.next;
        if (node.next != null) node.next.prev = node.prev;
        if (node == head) head = node.next;
        if (node == tail) tail = node.prev;
        size--;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T value) {
            this.value = value;
        }
    }
}