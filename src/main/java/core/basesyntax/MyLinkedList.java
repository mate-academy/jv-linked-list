package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        final Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = searchNode(index);
        Node<T> newPrev = node.prev;
        Node<T> newNode = new Node<>(newPrev, value, node);
        node.prev = newNode;
        if (newPrev == null) {
            first = newNode;
        } else {
            newPrev.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return searchNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> removedNode = searchNode(index);
        final T oldValue = removedNode.item;
        removedNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(searchNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> lostNode = first;
        while (lostNode != null) {
            if (lostNode.item == object || lostNode.item != null && lostNode.item.equals(object)) {
                unlink(lostNode);
                return true;
            }
            lostNode = lostNode.next;
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

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> previous = node.prev;
        final Node<T> nextOne = node.next;
        if (previous == null) {
            first = nextOne;
        } else {
            previous.next = nextOne;
            node.prev = null;
        }
        if (nextOne == null) {
            last = previous;
        } else {
            nextOne.prev = previous;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private Node<T> searchNode(int index) {
        checkIndex(index);
        Node<T> lostNode;
        if (index < size / 2) {
            lostNode = first;
            for (int i = 0; i < index; i++) {
                lostNode = lostNode.next;
            }
            return lostNode;
        }
        lostNode = last;
        for (int i = 1; i < size - index; i++) {
            lostNode = lostNode.prev;
        }
        return lostNode;
    }
}
