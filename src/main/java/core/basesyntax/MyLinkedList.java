package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int listSize;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        listSize = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (listSize == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        listSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index == listSize) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            listSize++;
        } else {
            Node<T> current = findNode(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
            listSize++;
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
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNode(index);
        T removedValue = current.item;
        unlink(current);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < listSize; i++) {
            if (current.item == object
                    || current.item.equals(object)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= listSize) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
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

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < (listSize >> 1)) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = listSize - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> removedNode) {
        if (listSize == 1) {
            first = null;
            last = null;
        } else if (removedNode == first) {
            first.next.prev = null;
            first = removedNode.next;
        } else if (removedNode == last) {
            last.prev.next = null;
            last = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
            removedNode.prev.next = removedNode.next;
        }
        listSize--;
    }
}
