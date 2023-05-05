package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            addFirst(newNode);
        } else {
            addTail(newNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newHead = new Node<>(head, value, null);
            head.previous = newHead;
            head = newHead;
        } else {
            Node<T> nodeByIndex = searchNode(index);
            Node<T> previousNode = nodeByIndex.previous;
            newNode = new Node<>(nodeByIndex, value, previousNode);
            nodeByIndex.previous = newNode;
            previousNode.next = newNode;
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
        return searchNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = searchNode(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> deletedNode = searchNode(index);
        unlink(deletedNode);
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object || (currentNode.value != null
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("An incorrect index was entered - " + index);
        }
    }

    private Node<T> searchNode(int index) {
        checkIndex(index);
        int indexNode = 0;
        Node<T> currentNode = head;
        while (indexNode != index) {
            currentNode = currentNode.next;
            indexNode++;
        }
        return currentNode;
    }

    private void addTail(Node<T> newNode) {
        newNode.previous = tail;
        tail.next = newNode;
        tail = newNode;
    }

    private void addFirst(Node<T> newNode) {
        head = newNode;
        tail = newNode;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
        } else if (node == tail) {
            tail = tail.previous;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> next, T value, Node<T> previous) {
            this.next = next;
            this.value = value;
            this.previous = previous;
        }
    }
}
