package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> indexNode = getByIndex(index);
        if (indexNode.prev == null) {
            linkFirst(value, indexNode);
        } else {
            linkMiddle(value, indexNode);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        final T removedItem = getByIndex(index).item;
        getByIndex(index).item = value;
        return removedItem;
    }

    @Override
    public T remove(int index) {
        Node<T> removedItem = getByIndex(index);
        unlink(removedItem);
        return removedItem.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = findByIndex(object);
        if (removedNode != null) {
            unlink(removedNode);
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " doesn't exist");
        }
    }

    private Node<T> findByIndex(T searchingItem) {
        Node<T> node = first;
        while (node != null) {
            T existingItem = node.item;
            if (areItemsEqual(existingItem, searchingItem)) {
                return node;
            } else {
                node = node.next;
            }
        }
        return null;
    }

    private Node<T> getByIndex(int index) {
        validateIndex(index);
        Node<T> wantedNode;
        if (index < (size >> 1)) {
            wantedNode = first;
            for (int i = 0; i < index; i++) {
                wantedNode = wantedNode.next;
            }
        } else {
            wantedNode = last;
            for (int i = size - 1; i > index; i--) {
                wantedNode = wantedNode.prev;
            }
        }
        return wantedNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            unlinkFirst(node);
        } else if (node.next == null) {
            unlinkLast(node);
        } else {
            unlinkMiddle(node);
        }
        size--;
    }

    private void unlinkFirst(Node<T> node) {
        Node<T> nextNode = node.next;
        if (nextNode != null) {
            nextNode.prev = null;
            node.next = null;
            first = nextNode;
        } else {
            first = null;
            last = null;
        }
    }

    private void unlinkLast(Node<T> node) {
        Node<T> previousNode = node.prev;
        previousNode.next = null;
        node.prev = null;
        last = previousNode;
    }

    private void unlinkMiddle(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        previousNode.next = nextNode;
        nextNode.prev = previousNode;
    }

    private void linkFirst(T value, Node<T> node) {
        Node<T> newNode = new Node<>(null, value, node);
        node.prev = newNode;
        first = newNode;
        size++;
    }

    private void linkMiddle(T value, Node<T> indexNode) {
        Node<T> newNode = new Node<>(indexNode.prev, value, indexNode);
        indexNode.prev.next = newNode;
        indexNode.prev = newNode;
        size++;
    }

    private boolean areItemsEqual(T existingItem, T searchingItem) {
        return existingItem == searchingItem || existingItem != null && existingItem.equals(searchingItem);
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
