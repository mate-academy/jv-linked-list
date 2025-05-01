package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
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
            Node<T> next = getNodeByIndex(index);
            insertBefore(next, value);
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
        Node<T> nodeToSet = getNodeByIndex(index);
        T oldValue = nodeToSet.element;
        nodeToSet.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeByIndex(index);
        return unlinkNode(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
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

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element, Node<T> prev, Node<T> next) {
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
        Node<T> newNode = new Node<>(elem, null, head);
        Node<T> oldNode = head;
        head = newNode;
        if (oldNode == null) {
            tail = newNode;
        } else {
            oldNode.prev = newNode;
        }
        size++;
    }

    private void addLast(T elem) {
        Node<T> newNode = new Node<>(elem, tail, null);
        Node<T> oldNode = tail;
        tail = newNode;
        if (oldNode == null) {
            head = newNode;
        } else {
            oldNode.next = newNode;
        }
        size++;
    }

    private T unlinkNode(Node<T> nodeToUnlink) {
        final T element = nodeToUnlink.element;
        Node<T> prev = nodeToUnlink.prev;
        Node<T> next = nodeToUnlink.next;

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

    private void insertBefore(Node<T> next, T elem) {
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(elem, prev, next);
        next.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (size / 2 < index) {
            return searchFromHead(index);
        }
        return searchFromTail(index);
    }

    private Node<T> searchFromHead(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> node = tail;
        for (int i = index; i < size - 1; i++) {
            node = node.prev;
        }
        return node;
    }
}

