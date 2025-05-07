package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstNode(value);
        } else {
            Node newNode = new Node<>(tail, value, null);
            newNode.prevNode.nextNode = newNode;
            tail = newNode;
            size++;
        }

    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            addFirstNode(value);
            return;
        }
        // add value in the middle
        Node currentNode = getNode(index);
        Node newNode = new Node<>(currentNode.prevNode, value, currentNode);
        newNode.prevNode.nextNode = newNode;
        currentNode.prevNode = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listItem : list) {
            add(listItem);
        }
    }

    @Override
    public T get(int index) {
        return (T) getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node currentNode = getNode(index);
        T resultItem = (T) currentNode.item;
        currentNode.item = value;
        return resultItem;
    }

    @Override
    public T remove(int index) {
        Node currentNode = getNode(index);
        unlink(currentNode);
        return (T) currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        while (currentNode != null) {
            if (equalElements((T) currentNode.item, object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.nextNode;
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
            throw new IndexOutOfBoundsException("Index not found " + index);
        }
    }

    private boolean equalElements(T element1, T element2) {
        return element1 == element2 || element1 != null && element1.equals(element2);
    }

    private <T> void addFirstNode(T value) {
        Node newNode = new Node(null, value, head);
        if (head != null) {
            head.prevNode = newNode;
        }
        head = newNode;
        if (size == 0) {
            tail = newNode;
        }
        size++;
    }

    private Node getNode(int index) {
        checkIndex(index);
        Node currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.nextNode;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prevNode;
            }
        }
        return currentNode;
    }

    private void unlink(Node node) {
        if (head == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            node.nextNode.prevNode = null;
            head = node.nextNode;
        } else if (node == tail) {
            node.prevNode.nextNode = null;
            tail = node.prevNode;
        } else {
            node.prevNode.nextNode = node.nextNode;
            node.nextNode.prevNode = node.prevNode;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> prevNode;
        private T item;
        private Node<T> nextNode;

        public Node(Node<T> prevNode, T item, Node<T> nextNode) {
            this.prevNode = prevNode;
            this.item = item;
            this.nextNode = nextNode;
        }
    }
}
