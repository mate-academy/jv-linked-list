package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> tail;
    private Node<T> head;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node newNode = new Node(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            Node newNode = new Node(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        Node<T> currentNode;
        if (index == size) {
            add(value);
            return;
        }
        currentNode = getNode(index);
        if (index == 0) {
            newNode = new Node(null, value, currentNode);
            size++;
            head = newNode;
            return;
        }
        newNode = new Node(currentNode.prev, value, currentNode);
        size++;
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
    }

    @Override
    public void addAll(List<T> myList) {
        for (T itemFromList: myList) {
            add(itemFromList);
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode;
        currentNode = getNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode;
        currentNode = getNode(index);
        T previousValue = currentNode.value;
        currentNode.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfSize(index);
        Node<T> oldNode;
        Node<T> currentNode;
        currentNode = getNode(index);
        oldNode = currentNode;
        unlink(currentNode);
        return oldNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            currentNode = getNode(i);
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                remove(i);
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

    private void unlink(Node node) {
        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndexOutOfSize(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of LinkedList range ");
        }
    }

    private Node<T> getNode(int index) {
        checkIndexOutOfSize(index);
        Node<T> currentNode;
        if (index < size >> 1) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }
}
