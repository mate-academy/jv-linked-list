package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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

    @Override
    public void add(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
            size++;
        } else {
            Node<T> current = new Node<>(getNodeByIndex(index).prev, value, getNodeByIndex(index));
            current.next.prev = current;
            current.prev.next = current;
            size++;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current = head;
        if ((size / 2) > index) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > index + 1; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        T element = getNodeByIndex(index).item;
        getNodeByIndex(index).item = value;
        return element;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T removedValue = currentNode.item;
        if (size == 1) {
            head = null;
            tail = null;
            size = 0;
            return removedValue;
        }
        unlink(currentNode);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == null && object == null
                    || current.item != null && current.item.equals(object)) {
                remove(i);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void unlink(Node<T> x) {
        if (x.next == null) {
            tail = x.prev;
            x.prev.next = null;
        } else {
            x.next.prev = x.prev;
        }
        if (x.prev == null) {
            head = x.next;
        } else {
            x.prev.next = x.next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("There is no element on this index: " + index);
        }
    }

}
