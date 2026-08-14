package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            first = node;
            last = first;
        } else {
            node.prev = last;
            last.next = node;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.prev != null) {
            node.prev.next = newNode;
        } else {
            first = newNode;
        }
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNode(index).item;
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T removedItem = node.item;
        unlink(node);
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        return unlink(node);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = 0; i < size - 1 - index; i++) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> getNode(T object) {
        Node<T> current = first;
        while (current != null) {
            if (object == current.item || (object != null && object.equals(current.item))) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private boolean unlink(Node<T> node) {
        if (node == null) {
            return false;
        }
        if (last == first && first != null) {
            first = null;
            last = null;
            size--;
            return true;
        }
        if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.item = null;
        node.next = null;
        size--;
        return true;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
