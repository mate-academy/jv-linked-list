package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    private void throwException(int index){
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds for length");
        }
    }

    private Node<T> getNodeByIndex(int targetIndex) {
        throwException(targetIndex);
        Node<T> currentNode = tail;
        for (int i = size - 1; i != targetIndex; i--) {
            currentNode = currentNode.previous;
        }
        return currentNode;
    }
    private Node<T> getNodeByValue(T value) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == value || node.value != null && node.value.equals(value)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }
    public void unlink(Node<T> removeNode) {
        Node<T> nodePrev = removeNode.previous;
        Node<T> nodeNext = removeNode.next;
        if (nodePrev == null) {
            head = nodeNext;
        } else {
            nodePrev.next = nodeNext;
        }
        if (nodeNext == null) {
            tail = nodePrev;
        } else {
            nodeNext.previous = nodePrev;
        }
        size--;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(null, value, null);
        if (head == null) {
            head = node;
        } else {
            node.previous = tail;
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head.previous = new Node<>(null, value, head);
            head = head.previous;
        } else {
            Node<T> validNode = getNodeByIndex(index);
            Node<T> node = new Node<T>(validNode.previous, value, validNode);
            validNode.previous = node;
            node.next.previous = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldVal = node.value;
        node.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        Node<T> removeTarget = getNodeByIndex(index);
        unlink(removeTarget);
        return removeTarget.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNodeByValue(object);
        if (node == null) {
            return false;
        }
        unlink(node);
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

}