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

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size > 0) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == size) {
            add(value);
            return;
        } else {
            linkBefore(value, getNode(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNode(index);
        T oldItem = newNode.item;
        newNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToBeRemoved = getNode(object);
        if (nodeToBeRemoved != null) {
            unlink(nodeToBeRemoved);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> nodeWeNeed = head;
        if (index >= size >> 1) {
            nodeWeNeed = tail;
            while (index < size - 1) {
                nodeWeNeed = nodeWeNeed.prev;
                index++;
            }
        } else {
            while (index > 0) {
                nodeWeNeed = nodeWeNeed.next;
                index--;
            }
        }
        return nodeWeNeed;
    }

    private Node<T> getNode(T value) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).item == value || value != null && value.equals(getNode(i).item)) {
                return getNode(i);
            }
        }
        return null;
    }

    private void linkBefore(T item, Node<T> nextNode) {
        final Node<T> previousNode = nextNode.prev;
        final Node<T> newNode = new Node<>(previousNode, item, nextNode);
        nextNode.prev = newNode;
        if (previousNode == null) {
            head = newNode;
        } else {
            previousNode.next = newNode;
        }
    }

    private T unlink(Node<T> node) {
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
        return node.item;
    }
}

