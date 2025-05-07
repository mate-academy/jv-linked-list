package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

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
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldVal = node.value;
        node.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T value) {
        int index = indexOf(value);
        if (index < 0) {
            return false;
        }
        remove(index);
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

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index out of bounds!!!");
        }
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int secondIndex = 0; secondIndex < index; secondIndex++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int secondIndex = size - 1; secondIndex > index; secondIndex--) {
                node = node.prev;
            }
        }
        return node;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void linkLast(T element) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, element, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> such) {
        Node<T> pred = such.prev;
        Node<T> newNode = new Node<>(pred, element, such);
        such.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
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
        node.value = null;
        size--;
        return element;
    }

    private int indexOf(T element) {
        int index = 0;
        for (Node<T> node = first; node != null; node = node.next) {
            if (element == null && node.value == null) {
                return index;
            } else if (element != null && element.equals(node.value)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
