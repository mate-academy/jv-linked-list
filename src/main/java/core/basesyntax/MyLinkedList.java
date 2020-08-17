package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(last, value, null);
        if (size == 0) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
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
        Node<T> oldNode = getNodeByIndex(index);

        Node<T> newNode = new Node<T>(oldNode.prev, value, oldNode);
        if (index > 0) {
            newNode.prev.next = newNode;
        } else {
            first = newNode;
        }
        oldNode.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        Node<T> node = getNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> node = getNodeByIndex(index);;
        T previosItem = node.item;
        node.item = value;
        return previosItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> node = getNodeByIndex(index);
        T removedItem = node.item;
        unlink(node);
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (t == null ? node.item == t : node.item.equals(t)) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private void unlink(Node<T> node) {
        if (first.equals(node)) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }

        node.prev = null;
        node.next = null;
        node.item = null;
    }

    private boolean checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
        return true;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
