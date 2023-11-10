package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkExclusively(index);
        if (index == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            linkLast(value);
            return;
        }
        Node<T> node = new Node<>(getNode(index).prev, value, getNode(index));
        getNode(index).prev.next = node;
        getNode(index).prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkInclusively(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkInclusively(index);
        T oldValue = getNode(index).item;
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkInclusively(index);
        T removedValue = getNode(index).item;
        unlink(getNode(index));
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size(); i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                unlink(node);
                return true;
            } else {
                node = node.next;
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

    private void linkFirst(T item) {
        Node<T> oldFirst = first;
        Node<T> newFirst = new Node<>(null, item, oldFirst);
        first = newFirst;
        if (oldFirst == null) {
            last = newFirst;
        } else {
            oldFirst.prev = newFirst;
        }
        size++;
    }

    private void linkLast(T item) {
        Node<T> oldLast = last;
        Node<T> newLast = new Node<>(oldLast, item, null);
        last = newLast;
        if (oldLast == null) {
            first = newLast;
        } else {
            oldLast.next = newLast;
        }
        size++;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
    }

    public Node<T> getNode(int index) {
        checkExclusively(index);
        Node<T> current;
        if (index < (size / 2)) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkExclusively(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(indexMsq(index));
        }
    }

    private void checkInclusively(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(indexMsq(index));
        }
    }

    private String indexMsq(int index) {
        return "There is no element with index " + index;
    }
}
