package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> top;
    private Node<T> bottom;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            linkTop(value);
        } else {
            linkBottom(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == 0 || size == 0) {
            linkTop(value);
        } else if (index == size) {
            linkBottom(value);
        } else {
            Node<T> k = getNodeByIndex(index);
            Node<T> newNode = new Node<>(k.prev, value, k);
            k.prev.next = newNode;
            k.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T item : list) {
                add(item);
            }
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> k = getNodeByIndex(index);
        T updatedValue = k.item;
        k.item = value;
        return updatedValue;
    }

    @Override
    public T remove(int index) {
        return unLink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> k = top; k != null; k = k.next) {
            if ((object == null && k.item == null)
                    || (object != null && object.equals(k.item))) {
                unLink(k);
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

    private void linkTop(T value) {
        Node<T> oldTop = top;
        Node<T> newNode = new Node<>(null, value, oldTop);
        top = newNode;
        if (oldTop != null) {
            oldTop.prev = newNode;
        } else {
            bottom = newNode;
        }
        size++;
    }

    private void linkBottom(T value) {
        Node<T> oldBottom = bottom;
        Node<T> newNode = new Node<>(oldBottom, value, null);
        bottom = newNode;
        if (oldBottom != null) {
            oldBottom.next = newNode;
        } else {
            top = newNode;
        }
        size++;
    }

    private T unLink(Node<T> node) {
        final T removedItem = node.item;
        if (node.prev == null) {
            top = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            bottom = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return removedItem;
    }

    private void checkItemIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index " + index + " out of size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index " + index + " out of size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkItemIndex(index);
        Node<T> k;
        if (index < (size / 2)) {
            k = top;
            for (int i = 0; i < index; i++) {
                k = k.next;
            }
        } else {
            k = bottom;
            for (int i = size - 1; i > index; i--) {
                k = k.prev;
            }
        }
        return k;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
