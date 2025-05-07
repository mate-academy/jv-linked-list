package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        private Node(Node<T> previous, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong Index.");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findNode(index);
        Node<T> addNode = new Node<>(currentNode.previous, value, currentNode);
        if (index != 0) {
            currentNode.previous.next = addNode;
        } else {
            head = addNode;
        }
        currentNode.previous = addNode;
        size++;
        return;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNode(index);
        T tempItem = currentNode.item;
        currentNode.item = value;
        return tempItem;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNode(index);
        unlinkNode(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> searchNode = head;
        while (searchNode != null) {
            if (searchNode.item == object
                    || (searchNode.item != null && searchNode.item.equals(object))) {
                unlinkNode(searchNode);
                return true;
            }
            searchNode = searchNode.next;
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

    private void unlinkNode(Node node) {
        if (node.previous == null) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }
        if (node.next == null) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
    }

    private Node<T> findNode(int index) {
        checkIndexValidity(index);
        if (index <= size / 2) {
            Node<T> currentNode = head;
            int i = 0;
            while (currentNode != null) {
                if (index == i) {
                    return currentNode;
                }
                currentNode = currentNode.next;
                i++;
            }
        }
        if (index > size / 2) {
            Node<T> currentNode = tail;
            int i = size - 1;
            while (currentNode != null) {
                if (index == i) {
                    return currentNode;
                }
                currentNode = currentNode.previous;
                i--;
            }
        }
        return null;
    }

    private void checkIndexValidity(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }
}
