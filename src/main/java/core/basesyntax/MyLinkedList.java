package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newlast = last;
        Node<T> newNode = new Node<>(last, value, null);
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
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == size) {
            add(value);
        } else {
            final Node<T> pred = findNodeByIndex(index).prev;
            final Node<T> newNode = new Node<>(pred, value, findNodeByIndex(index));
            findNodeByIndex(index).prev = newNode;
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> rewrite = findNodeByIndex(index);
        T oldValue = rewrite.item;
        rewrite.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return removeByObject(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = first; i != null; i = i.next) {
            if (i.item == object || object != null && object.equals(i.item)) {
                removeByObject(i);
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

    private Node<T> findNodeByIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
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
