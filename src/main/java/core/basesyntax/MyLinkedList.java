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
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T l : list) {
            add(l);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        final T oldValue = getNode(index).item;
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (object == node.item || object != null && object.equals(node.item)) {
                unlink(node);
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

    private Node<T> getNode(int index) {
        checkElementIndex(index);
        Node<T> indexedNode;
        if (index < (size >> 1)) {
            indexedNode = first;
            for (int i = 0; i < index; i++) {
                indexedNode = indexedNode.next;
            }
        } else {
            indexedNode = last;
            for (int i = size - 1; i > index; i--) {
                indexedNode = indexedNode.prev;
            }
        }
        return indexedNode;
    }

    void linkLast(T value) {
        final Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    void linkBefore(T value, Node<T> indexedNote) {
        final Node<T> newNode = new Node<>(indexedNote.prev, value, indexedNote);
        if (indexedNote.prev == null) {
            first = newNode;
        } else {
            indexedNote.prev.next = newNode;
        }
        indexedNote.prev = newNode;
        size++;
    }

    T unlink(Node<T> x) {
        final T unlinkedElement = x.item;
        if (x == first) {
            first = x.next;
        } else if (x == last) {
            last = x.prev;
        } else {
            x.next.prev = x.prev;
            x.prev.next = x.next;
            x.prev = null;
            x.next = null;
        }
        x.item = null;
        size--;
        return unlinkedElement;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
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
