package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds " + size);
        }

        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, iterNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return iterNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeByIndex = iterNode(index);
        T outValue = nodeByIndex.item;
        nodeByIndex.item = value;
        return outValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> nodeByIndex = iterNode(index);
        return deleteLink(nodeByIndex);
    }

    @Override
    public boolean remove(T object) {
        Node<T> fromFirstNode = first;
        while (fromFirstNode != null) {
            if (object == null && fromFirstNode.item == null) {
                deleteLink(fromFirstNode);
                return true;
            } else if (object != null && object.equals(fromFirstNode.item)) {
                deleteLink(fromFirstNode);
                return true;
            }
            fromFirstNode = fromFirstNode.next;
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

    private void linkBefore(T value, Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        node.prev = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private Node<T> iterNode(int index) {
        checkElementIndex(index);
        Node<T> node = first;
        int i = 0;
        while (i < index) {
            node = node.next;
            i++;
        }
        return node;
    }

    private T deleteLink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        T element = node.item;
        node.item = null;
        size--;
        return element;
    }

    private void linkLast(T value) {
        final Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds " + size);
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
