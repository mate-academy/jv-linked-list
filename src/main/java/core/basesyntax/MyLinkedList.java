package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;

        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> target = node(index);
        Node<T> prev = target.prev;

        Node<T> newNode = new Node<>(prev, value, target);
        target.prev = newNode;

        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> target = node(index);
        T oldValue = target.item;
        target.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;

        if (object == null) {
            while (current != null) {
                if (current.item == null) {
                    unlink(current);
                    return true;
                }
                current = current.next;
            }
        } else {
            while (current != null) {
                if (object.equals(current.item)) {
                    unlink(current);
                    return true;
                }
                current = current.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
