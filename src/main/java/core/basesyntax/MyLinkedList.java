package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head = node;
            if (size == 0) {
                tail = node;
            }
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = getCurrentNode(index);
            Node<T> node = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = node;
            currentNode.prev = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        T[] array = (T[]) list.toArray();
        for (T element : array) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return (T) getCurrentNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getCurrentNode(index);
        T result = currentNode.item;
        currentNode.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node node = getCurrentNode(object);
        if (node != null) {
            unlink(node);
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

    private void unlink(Node node) {
        if (node == head) {
            head = node.next;
        } else if (node == tail) {
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    private Node getCurrentNode(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private Node getCurrentNode(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
