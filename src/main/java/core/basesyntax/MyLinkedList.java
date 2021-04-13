package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        if (head == null) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            linkFirst(value);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T currentNode : list) {
            add(currentNode);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T returnValue = currentNode.item;
        currentNode.item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T item) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == item
                    || currentNode.item != null && currentNode.item.equals(item)) {
                remove(i);
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
            throw new IndexOutOfBoundsException("For index: " + index + " Size: " + size);
        }
    }

    private void linkFirst(T value) {
        if (head != null) {
            head = new Node<>(null, value, head);
        } else {
            Node<T> firstNode = new Node<>(null, value, null);
            head = firstNode;
            tail = firstNode;
        }
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index <= (size >> 1)) {
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

    private T unlink(Node<T> nodeToBeRemoved) {
        if (head.equals(nodeToBeRemoved)) {
            if (size == 1) {
                head = null;
                tail = null;
            }
            head = nodeToBeRemoved.next;
        } else if (nodeToBeRemoved.next != null) {
            nodeToBeRemoved.next.prev = nodeToBeRemoved.prev;
            nodeToBeRemoved.prev.next = nodeToBeRemoved.next;
        }
        size--;
        return nodeToBeRemoved.item;
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
