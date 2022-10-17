package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        final Node<T> previous = last;
        final Node<T> newNode = new Node<>(previous, value, null);
        last = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            final Node<T> nodeAtIndex = findNode(index);
            final Node<T> previous = nodeAtIndex.prev;
            final Node<T> newNode = new Node<>(previous, value, nodeAtIndex);
            nodeAtIndex.prev = newNode;
            if (previous == null) {
                first = newNode;
            } else {
                previous.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t: list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = findNode(index);
        T oldVal = oldNode.item;
        oldNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (int index = 0; index < size; index++) {
            final Node<T> nodeAtIndex = findNode(index);
            if (object == nodeAtIndex.item
                    || object != null && object.equals(nodeAtIndex.item)) {
                unlink(nodeAtIndex);
                return true;
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

    private Node<T> findNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> indexNode;
        if (index < (size >> 1)) {
            indexNode = first;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = last;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode;
    }

    private T unlink(Node<T> needRemove) {
        final T value = needRemove.item;
        final Node<T> next = needRemove.next;
        final Node<T> prev = needRemove.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            needRemove.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            needRemove.next = null;
        }

        needRemove.item = null;
        size--;
        return value;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
