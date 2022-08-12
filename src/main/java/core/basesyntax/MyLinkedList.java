package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> currentNode = new Node<>(tail, value, null);
        if (head == null) {
            head = currentNode;
        } else {
            tail.next = currentNode;
        }
        tail = currentNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            Node<T> findNode = searchNode(index);
            Node<T> prevNode = findNode.previous;
            Node<T> newNode = new Node<>(prevNode, value, findNode);
            findNode.previous = newNode;
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
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = searchNode(index);
        T replacedValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeByIndex = searchNode(index);
        unlink(nodeByIndex);
        size--;
        return nodeByIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> first = head;
        if (first == null) {
            throw new IndexOutOfBoundsException("Don`t have object " + object);
        }
        for (int i = 0; i < size; i++) {
            if (first != null && (first.value == object || first.value.equals(object))) {
                unlink(first);
                size--;
                return true;
            }
            first = first.next;
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
            throw new IndexOutOfBoundsException("Don't have such index " + index);
        }
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.previous;
        if (size == 1) {
            head = null;
            tail = null;
            return;
        }
        if (node == head) {
            head = nextNode;
            nextNode.previous = null;
            return;
        }
        if (node == tail) {
            prevNode.next = null;
            return;
        }
        prevNode.next = node.next;
        nextNode.previous = node.previous;
    }

    private Node<T> searchNode(int index) {
        Node<T> node;
        if (index < (size / 2)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> previous;
        private T value;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.next = next;
            this.previous = previous;
            this.value = value;
        }
    }
}
