package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newlast = last;
        Node<T> newNode = new Node<>(newlast, value, null);
        last = newNode;
        if (newlast == null) {
            first = newNode;
        } else {
            newlast.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            final Node<T> pred = node(index).prev;
            final Node<T> newNode = new Node<>(pred, value, node(index));
            node(index).prev = newNode;
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size();i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> rewrite = node(index);
        T oldValue = rewrite.item;
        rewrite.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return removeByObject(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = first; i != null; i = i.next) {
                if (i.item == null) {
                    removeByObject(i);
                    return true;
                }
            }
        } else {
            for (Node<T> i = first; i != null; i = i.next) {
                if (object.equals(i.item)) {
                    removeByObject(i);
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

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> nodeByIndex = first;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
            return nodeByIndex;
        } else {
            Node<T> nodeByIndex = last;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
            return nodeByIndex;
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkPositionIndexForAdd(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private T removeByObject(Node<T> node) {
        final T element = node.item;
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
        size--;
        node.item = null;
        return element;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
