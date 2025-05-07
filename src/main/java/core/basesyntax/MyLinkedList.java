package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
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

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            final Node<T> nextNode = getNodeByIndex(index);
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T currentItem = currentNode.item;
        currentNode.item = value;
        return currentItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null && currentNode.item == null
                    || object != null && object.equals(currentNode.item)) {
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

    public Node<T> getNodeByIndex(int index) {
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

    private T unlink(Node<T> node) {
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
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index
                    + " , out of bound, array length - " + size);
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
