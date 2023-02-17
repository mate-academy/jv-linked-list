package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T currentValue = node.item;
        node.item = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = getNodeByItem(object);
        if (removedNode == null) {
            return false;
        }
        unlink(removedNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void linkLast(T value) {
        if (last == null) {
            first = new Node<>(value, null, null);
            last = first;
        } else {
            last.next = new Node<>(value, null, last);
            last = last.next;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> current) {
        Node<T> newNode = new Node<>(value, current, current.prev);
        if (current.prev == null) {
            first = newNode;
        } else {
            current.prev.next = newNode;
        }
        current.prev = newNode;
        size++;
    }

    private T unlink(Node<T> removedNode) {
        Node<T> next = removedNode.next;
        Node<T> prev = removedNode.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return removedNode.item;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
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

    private Node<T> getNodeByItem(T item) {
        Node<T> node = first;
        while (node != null) {
            if (node.item == null ? item == null : node.item.equals(item)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
