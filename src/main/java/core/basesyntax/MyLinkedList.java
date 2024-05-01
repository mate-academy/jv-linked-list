package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        rangeCheckForAdd(index);
        if (index == size) {
            linkLast(value);
        } else {
            Node<T> nodeByIndex = findNodeWithIndex(index);
            linkBefore(value,nodeByIndex);
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
        checkIndex(index);
        Node<T> nodeByIndex = findNodeWithIndex(index);
        return nodeByIndex.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = findNodeWithIndex(index);
        T valueFromNode = nodeByIndex.item;
        nodeByIndex.item = value;
        return valueFromNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeByIndex = findNodeWithIndex(index);
        return unlink(nodeByIndex);
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = head; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    unlink(node);
                    return true;
                }
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

    void linkLast(T element) {
        Node<T> previousTail = tail;
        Node<T> newNode = new Node<>(previousTail, element, null);
        tail = newNode;
        if (previousTail == null) {
            head = newNode;
        } else {
            previousTail.next = newNode;
        }
        size++;
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    private Node<T> findNodeWithIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    void linkBefore(T e, Node<T> nodeWithIndex) {
        Node<T> previousNode = nodeWithIndex.prev;
        Node<T> newNode = new Node<>(previousNode, e, nodeWithIndex);
        nodeWithIndex.prev = newNode;
        if (previousNode == null) {
            head = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    T unlink(Node<T> nodeByIndex) {
        final T element = nodeByIndex.item;
        Node<T> next = nodeByIndex.next;
        Node<T> prev = nodeByIndex.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            nodeByIndex.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            nodeByIndex.next = null;
        }
        nodeByIndex.item = null;
        size--;
        return element;
    }
}
