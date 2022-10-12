package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    public MyLinkedList() {
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

    @Override
    public void add(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
        } else {
            final Node<T> previous = node(index).prev;
            final Node<T> newNode = new Node<>(previous, value, node(index));
            node(index).prev = newNode;
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

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    Node<T> node(int index) {
        Node<T> indexNode;
        if (index < (size >> 1)) {
            indexNode = first;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
            return indexNode;
        } else {
            indexNode = last;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
            return indexNode;
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> oldNode = node(index);
        T oldVal = oldNode.item;
        oldNode.item = value;
        return oldVal;
    }

    T unlink(Node<T> needRemove) {
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

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> needRemove = first; needRemove != null; needRemove = needRemove.next) {
                if (needRemove.item == null) {
                    unlink(needRemove);
                    return true;
                }
            }
        } else {
            for (Node<T> needRemove = first; needRemove != null; needRemove = needRemove.next) {
                if (object.equals(needRemove.item)) {
                    unlink(needRemove);
                    return true;
                }
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
}
