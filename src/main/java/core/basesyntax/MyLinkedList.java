package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int NOT_FOUND = -1;
    private static final int ONE = 1;
    private static final int HALF = 2;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
        }
    }

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
        if (isValidIndex(index)) {
            Node<T> newNode = new Node<>(null, value, null);
            if (index == 0) {
                addToBegin(newNode);
                size++;
            } else if (index == size) {
                add(value);
            } else {
                addToMiddle(newNode, index);
                size++;
            }
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
        if (index >= 0 && index < size) {
            if (firstHalf(index)) {
                int indexFirstHalf = index + ONE;
                newNode = head;
                newNode = firstHalfFindNode(newNode, indexFirstHalf);
            } else {
                newNode = tail;
                int indexFromEnd = size - index - ONE;
                newNode = secondHalfFindNode(newNode, indexFromEnd);
            }
            return newNode.item;
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }

    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        if (node != null) {
            T item = node.item;
            node.item = value;
            return item;
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        if (index == NOT_FOUND) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
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
        } else if (index == size - ONE) {
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
        int index = getNode(object);
        if (index != NOT_FOUND) {
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

    private boolean isValidIndex(int index) {
        if (index >= 0 && index <= size) {
            return true;
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private Node<T> firstHalfFindNode(Node<T> current, int index) {
        for (int i = 0; i < index - ONE; i++) {
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

    private void addToBegin(Node<T> newNode) {
        newNode.next = head;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        tail = newNode;
    }

    private void addToMiddle(Node<T> newNode, int index) {
        int half = size / HALF;
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
        return index <= size / HALF;
    }

    private Node<T> getNode(int index) {
        if (index >= 0 && index < size) {
            Node<T> newNode;
            if (firstHalf(index)) {
                int indexFirstHalf = index + ONE;
                newNode = head;
                newNode = firstHalfFindNode(newNode, indexFirstHalf);
            } else {
                newNode = tail;
                int indexFromEnd = size - index - ONE;
                newNode = secondHalfFindNode(newNode, indexFromEnd);
            }
            return newNode;
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private int getNode(T value) {
        Node<T> currentNode = head;
        int count = 0;
        while (currentNode != null) {
            if ((currentNode.item == null && value == null)
                    || (currentNode.item != null && currentNode.item.equals(value))) {
                return count;
            }
            count++;
            currentNode = currentNode.next;
        }
        return NOT_FOUND;
    }
}
