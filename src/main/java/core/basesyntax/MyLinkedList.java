package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int NOT_FOUND_INDEX = -1;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);

        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
            head.prev = null;
            tail.next = null;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, false);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            addToHead(newNode);
            size++;
        } else if (index == size) {
            add(value);
        } else {
            addToMiddle(newNode, index);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T el: list) {
            add(el);
        }
    }

    @Override
    public T get(int index) {
        Node<T> newNode = null;
        checkIndex(index, true);
        if (firstHalf(index)) {
            int indexFirstHalf = index + 1;
            newNode = head;
            newNode = firstHalfFindNode(newNode, indexFirstHalf);
        } else {
            newNode = tail;
            int indexFromEnd = size - index - 1;
            newNode = secondHalfFindNode(newNode, indexFromEnd);
        }
        return newNode.item;

    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T item = node.item;
        node.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        checkIndex(index, true);
        if (head == tail) {
            final T removedItem = head.item;
            head = null;
            tail = null;
            size = 0;
            return removedItem;
        }
        if (index == 0) {
            Node<T> nextNode = node.next;
            nextNode.prev = null;
            head = nextNode;
            size--;
        } else if (index == size - 1) {
            Node<T> prevNode = node.prev;
            prevNode.next = null;
            tail = prevNode;
            size--;
        } else {
            Node<T> prevNode = node.prev;
            Node<T> nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            size--;
        }

        return node.item;
    }

    @Override
    public boolean remove(T object) {
        int index = getNodeIndex(object);
        if (index != NOT_FOUND_INDEX) {
            remove(index);
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

    private void checkIndex(int index, boolean setOrRemove) {
        if (setOrRemove) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index out of bounds, index: " + index
                + "size: " + size);
            }
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds, index: " + index
                    + "size: " + size);
        }

    }

    private Node<T> firstHalfFindNode(Node<T> current, int index) {
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> secondHalfFindNode(Node<T> current, int index) {
        for (int i = 0; i < index; i++) {
            current = current.prev;
        }
        return current;
    }

    private void addToHead(Node<T> newNode) {
        newNode.next = head;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        tail = newNode;
    }

    private void addToMiddle(Node<T> newNode, int index) {
        Node<T> current;
        if (firstHalf(index)) {
            current = head;
            current = firstHalfFindNode(current, index);
        } else {
            int indexFromEnd = size - index;
            current = tail;
            current = secondHalfFindNode(current, indexFromEnd);
        }

        Node<T> nextNode = current.next;
        nextNode.prev = newNode;
        current.next = newNode;
        newNode.next = nextNode;
        newNode.prev = current;
    }

    private boolean firstHalf(int index) {
        return index <= size / 2;
    }

    private Node<T> getNode(int index) {
        checkIndex(index, true);
        Node<T> newNode;
        if (firstHalf(index)) {
            int indexFirstHalf = index + 1;
            newNode = head;
            newNode = firstHalfFindNode(newNode, indexFirstHalf);
        } else {
            newNode = tail;
            int indexFromEnd = size - index - 1;
            newNode = secondHalfFindNode(newNode, indexFromEnd);
        }
        return newNode;
    }

    private int getNodeIndex(T value) {
        Node<T> currentNode = head;
        int count = 0;
        while (currentNode != null) {
            if ((currentNode.item == value)
                    || (currentNode.item != null && currentNode.item.equals(value))) {
                return count;
            }
            count++;
            currentNode = currentNode.next;
        }
        return NOT_FOUND_INDEX;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }
}
