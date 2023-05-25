package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (first == null) {
            addFirst(value);
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            checkIndexIsValid(index);
            Node<T> oldNode = getNodeByIndex(index);
            Node<T> oldNodePrev = oldNode.prev;
            Node<T> newNode = new Node<>(oldNodePrev, value, oldNode);
            oldNode.prev = newNode;
            oldNodePrev.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        Object[] arrayList = list.toArray();
        if (arrayList.length == 0) {
            throw new IllegalArgumentException("Can't add an empty array.");
        }
        for (Object o : arrayList) {
            if (first == null && last == null) {
                Node<T> newNode = new Node<>(null, (T) o, null);
                first = newNode;
                last = newNode;
                size++;
            } else {
                Node<T> l = last;
                Node<T> newNode = new Node<>(l, (T) o, null);
                last = newNode;
                l.next = newNode;
                size++;
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndexIsValid(index);
        Node<T> wantedNode = getNodeByIndex(index);
        return wantedNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexIsValid(index);
        Node<T> wantedNode = getNodeByIndex(index);
        T oldValue = wantedNode.item;
        wantedNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexIsValid(index);
        Node<T> removedNode = getNodeByIndex(index);
        final T removedItem = removedNode.item;
        final Node<T> next = removedNode.next;
        final Node<T> prev = removedNode.prev;
        if (size == 1) {
            return removeOnlyOne(removedNode);
        }
        if (prev == null) {
            next.prev = null;
            first = next;
        } else if (next == null) {
            prev.next = null;
            last = prev;
        } else {
            prev.next = next;
            next.prev = prev;
        }
        size--;
        removedNode.item = null;
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> x = first;
        for (int i = 0; i < size; i++) {
            if ((x.item == object) || (x.item != null && x.item.equals(object))) {
                remove(i);
                return true;
            }
            x = x.next;
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

    private void checkIndexIsValid(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Can't add element on position " + index);
        }
    }

    private void addFirst(T value) {
        Node<T> f = first;
        Node<T> newNode = new Node<>(null, value, first);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        l.next = newNode;
        size++;
    }

    private T removeOnlyOne(Node<T> removedNode) {
        final T removedItem = removedNode.item;
        first = null;
        last = null;
        removedNode.item = null;
        size--;
        return removedItem;
    }

    private Node<T> getNodeByIndex(int index) {
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

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
