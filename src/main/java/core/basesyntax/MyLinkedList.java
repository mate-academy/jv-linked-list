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
        checkIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("The list is empty!");
        }
        for (var i : list) {
            this.add(i);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        checkIndexToSize(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        checkIndexToSize(index);
        final Node<T> node = node(index);
        final T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        checkIndexToSize(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if ((x.value == object) || (x.value != null && x.value.equals(object))) {
                unlink(x);
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

    private void checkIndexToSize(int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private T unlink(Node<T> node) {
        final T value = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
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
        node.value = null;
        size--;
        return value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private void linkLast(T value) {
        Node<T> oldLast = last;
        Node<T> newLast = new Node<>(value, null, oldLast);
        last = newLast;
        if (oldLast == null) {
            first = newLast;
        } else {
            oldLast.next = newLast;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> node) {
        if (node == null) {
            throw new RuntimeException("There is no such element to link before!");
        }
        Node<T> newNode = new Node<>(value, node, node.prev);
        if (node.prev == null) {
            first = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    private Node<T> node(int index) {
        checkIndex(index);
        Node<T> x;
        if (index < (size >> 1)) {
            x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }
}
