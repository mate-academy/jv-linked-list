package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    @Override
    public void add(T value) {
        Node nextElement = new Node(value, last, null);
        if (size == 0) {
            first = last = nextElement;
            size++;
            return;
        }
        last.next = nextElement;
        nextElement.prev = last;
        last = nextElement;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            Node nextElement = new Node(value, null, first);
            if (first == null) {
                first = last = nextElement;
            } else {
                first.prev = nextElement;
                first = nextElement;
            }
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node current = findNodeByIndex(index);
        Node previous = current.prev;
        Node newNode = new Node(value, previous, current);
        previous.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = findNodeByIndex(index);
        T oldCurrent = current.item;
        current.item = value;
        return oldCurrent;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = findNodeByIndex(index);
        final T oldCurrent = current.item;
        unLinked(current);
        size--;
        return oldCurrent;
    }

    @Override
    public boolean remove(T object) {
        Node current = first;
        while (current != null) {
            if (current.item == null && object == null
                    || current.item != null && current.item.equals(object)) {
                unLinked(current);
                size--;
                return true;
            }
            current = current.next;
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

    private Node findNodeByIndex(int index) {
        Node current;
        if (index < size / 2) {
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

    private void unLinked(Node current) {
        if (current.prev == null) {
            first = current.next;
        } else {
            current.prev.next = current.next;
        }
        if (current.next == null) {
            last = current.prev;
        } else {
            current.next.prev = current.prev;
        }
    }

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
