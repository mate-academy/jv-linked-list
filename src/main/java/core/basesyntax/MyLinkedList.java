package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            Node prev = getNodeByIndex(index);
            insertBefore(prev, value);
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
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node nodeToSet = getNodeByIndex(index);
        T oldValue = nodeToSet.element;
        nodeToSet.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node nodeToRemove = getNodeByIndex(index);
        return unlinkNode(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.element || object != null
                    && object.equals(currentNode.element)) {
                unlinkNode(currentNode);
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

    private class Node {
        private T element;
        private Node prev;
        private Node next;

        private Node(T element, Node prev, Node next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds, size " + size);
        }
    }

    private void addFirst(T elem) {
        Node newNode = new Node(elem, null, head);
        Node oldNode = head;
        head = newNode;
        if (oldNode == null) {
            tail = newNode;
        } else {
            oldNode.prev = newNode;
        }
        size++;
    }

    private void addLast(T elem) {
        Node newNode = new Node(elem, tail, null);
        Node oldNode = tail;
        tail = newNode;
        if (oldNode == null) {
            head = newNode;
        } else {
            oldNode.next = newNode;
        }
        size++;
    }

    private T unlinkNode(Node nodeToUnlink) {
        final T element = nodeToUnlink.element;
        Node prev = nodeToUnlink.prev;
        Node next = nodeToUnlink.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return element;
    }

    private void insertBefore(Node next, T elem) {
        Node prev = next.prev;
        Node newNode = new Node(elem, prev, next);
        next.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private Node getNodeByIndex(int index) {
        checkIndex(index);
        if (size / 2 < index) {
            return searchFromHead(index);
        }
        return searchFromTail(index);
    }

    private Node searchFromHead(int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node searchFromTail(int index) {
        Node node = tail;
        for (int i = index; i < size - 1; i++) {
            node = node.prev;
        }
        return node;
    }
}
