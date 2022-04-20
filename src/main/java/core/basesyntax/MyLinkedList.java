package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (first == null) {
            linkFirst(value);
            return;
        }
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            linkLast(value);
            return;
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeToSet = getNode(index);
        T oldVal = nodeToSet.value;
        nodeToSet.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if (object == x.value || object != null && object.equals(x.value)) {
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

    private T unlink(Node<T> nodeToErase) {
        final T removed = nodeToErase.value;
        final Node<T> next = nodeToErase.next;
        final Node<T> prev = nodeToErase.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            nodeToErase.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            nodeToErase.next = null;
        }
        nodeToErase.value = null;
        size--;
        return removed;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);
        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;

            }
            return x;
        }
    }

    private void linkFirst(T value) {
        Node<T> nextAfter = first;
        Node<T> newFirst = new Node<>(null, value, nextAfter);
        first = newFirst;
        if (nextAfter == null) {
            last = newFirst;
        } else {
            nextAfter.prev = newFirst;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> oldLast = last;
        Node<T> addedNode = new Node(oldLast, value, null);
        last = addedNode;
        if (oldLast == null) {
            first = addedNode;
        } else {
            oldLast.next = addedNode;
        }
        size++;
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("index " + index + " size " + size);
        }
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void linkBefore(T value, Node<T> beforeThis) {
        final Node<T> lastAfterNew = beforeThis.prev;
        final Node<T> newNode = new Node<>(lastAfterNew, value, beforeThis);
        beforeThis.prev = newNode;
        if (lastAfterNew == null) {
            first = newNode;
        } else {
            lastAfterNew.next = newNode;
        }
        size++;
    }
}
