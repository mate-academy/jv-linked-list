package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = last = new Node<>(value, null, null);
            ++size;
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            addAtIndex(value, index);
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
        return getByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getByIndex(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getByIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (node != null) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
                unlink(node);
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

    private Node<T> getByIndex(int index) {
        checkIndex(index);
        Node<T> node;
        if (index <= size / 2) {
            node = this.first;
            for (int i = 0; i < index; ++i) {
                node = node.next;
            }
        } else {
            node = this.last;
            for (int i = size - 1; i > index; --i) {
                node = node.prev;
            }
        }
        return node;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(value, null, first);
        first.prev = newNode;
        first = newNode;
        ++size;
    }

    private void addAtIndex(T value, int index) {
        Node<T> searched = getByIndex(index);
        Node<T> newNode = new Node<>(value, searched.prev, searched);
        searched.prev.next = newNode;
        searched.prev = newNode;
        ++size;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        last.next = newNode;
        last = newNode;
        ++size;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Size " + size + "! Index " + index + " wrong");
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        --size;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
