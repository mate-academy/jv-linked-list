package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (isEmpty()) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkEqualsSizeIndex(index);
        if (isEmpty() || index == size) {
            add(value);
            return;
        } else if (index == 0 && !isEmpty()) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> followingNode = getNodeByIndex(index);
            Node<T> previousNode = followingNode.prev;
            Node<T> newNode = new Node<>(previousNode, value, followingNode);
            previousNode.next = newNode;
            followingNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int a = 0; a < list.size(); a++) {
            add(list.get(a));
        }
    }

    @Override
    public T get(int index) {
        Node<T> variableNode = getNodeByIndex(index);
        return variableNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> variableNode = getNodeByIndex(index);
        T deletedValue = variableNode.value;
        variableNode.value = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T deletedValue = null;
        if (index == 0 && size > 1) {
            deletedValue = head.value;
            head = head.next;
        }
        if (size == 1) {
            deletedValue = head.value;
            head = null;
            tail = null;
        }
        if (index == size - 1 && index > 0) {
            deletedValue = tail.value;
            tail = tail.prev;
            tail.next = null;
        }
        if (size - 1 > index && size > 1 && index != 0) {
            deletedValue = getNodeByIndex(index).value;
            unLink(getNodeByIndex(index));
        }
        size--;
        return deletedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> variableNode = head;
        for (int a = 0; a < size; a++) {
            if (variableNode.value == object
                    || variableNode.value != null && variableNode.value.equals(object)) {
                remove(a);
                return true;
            }
            variableNode = variableNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> variableNode;
        if (index < size / 2) {
            variableNode = head;
            for (int a = 1; a <= index; a++) {
                variableNode = variableNode.next;
            }
        } else {
            variableNode = tail;
            for (int a = size - 2; a >= index; a--) {
                variableNode = variableNode.prev;
            }
        }
        return variableNode;
    }

    private void unLink(Node<T> node) {
        Node<T> deletedNode = node;
        Node<T> previousNode = deletedNode.prev;
        Node<T> followingNode = deletedNode.next;
        previousNode.next = followingNode;
        followingNode.prev = previousNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index
                    + " is not correction. size = " + size);
        }
    }

    private void checkEqualsSizeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index " + index
                    + " is not correction. size = " + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
