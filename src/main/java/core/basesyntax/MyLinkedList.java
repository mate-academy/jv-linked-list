package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = new Node<T>(null, value, null);
            last = first;
        } else {
            last.next = new Node<T>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> after = getNode(index);
        Node<T> before = after.prev;
        Node<T> newNode = new Node<>(before, value, after);
        if (before != null) {
            before.next = newNode;
        }
        after.prev = newNode;
        if (index == 0) {
            first = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> elementToRemove = getNode(index);
        unlink(elementToRemove);
        return elementToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.item == object || current.item != null
                    && current.item.equals(object)) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        if ((size / 2) > index) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size; i > (index + 1); i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        size--;
        if (node.next == null) {
            if (node.prev != null) {
                node.prev.next = null;
            }
            last = node.prev;
        } else if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }
}
