package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public void linkLast(T value) {
        Node<T> prevLink = tail;
        Node<T> newNode = new Node<>(prevLink, value, null);
        tail = newNode;
        if (prevLink == null) {
            head = newNode;
        } else {
            prevLink.next = newNode;
        }
        size++;
    }

    public void linkBefore(T value, Node<T> nextNode) {
        final Node<T> prevNode = nextNode.prev;
        final Node<T> newNode = new Node<>(prevNode, value, nextNode);
        nextNode.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    public T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index
                    + " , out of bound, array length - " + size);
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, checkNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return checkNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = checkNodeByIndex(index);
        T removableNode = currentNode.item;
        currentNode.item = value;
        return removableNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(checkNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.item == null) {
                    unlink(currentNode);
                    return true;
                }
            }
        } else {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.item)) {
                    unlink(currentNode);
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

    public Node<T> checkNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < (size >> 1)) {
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

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index
                    + " , out of bound, array length - " + (size - 1));
        }
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T item;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.item = item;
        }
    }
}
