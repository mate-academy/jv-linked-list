package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> foundNode = findNodeByIndex(index);
            Node<T> prevNode = foundNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, foundNode);
            foundNode.prev = newNode;
            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> foundNode = findNodeByIndex(index);
        T oldValue = foundNode.item;
        foundNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> foundNode = findNodeByIndex(index);
        return unlink(foundNode);
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexByValue(object);
        if (index == -1) {
            return false;
        } else {
            unlink(findNodeByIndex(index));
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int findIndexByValue(T value) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == null && value == null) {
                return i;
            }
            if (currentNode.item != null && currentNode.item.equals(value)) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ",size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ",size: " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> startNode = head;
            for (int i = 0; i < index; i++) {
                startNode = startNode.next;
            }
            return startNode;
        } else {
            Node<T> startNode = tail;
            for (int i = size - 1; i > index; i--) {
                startNode = startNode.prev;
            }
            return startNode;
        }
    }

    private T unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }

        T nodeItem = node.item;
        node.item = null;
        size--;
        return nodeItem;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
