package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node first;
    private Node last;
    private int size;

    @Override
    public void add(T value) {
        Node oldLast = last;
        last = new Node(value, null, oldLast);
        if (oldLast != null) {
            oldLast.next = last;
        } else {
            first = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            add(value);
            return;
        }

        Node next = getNode(index);
        Node prev = next.previous;
        Node newNode = new Node(value, next, prev);
        prev.next = newNode;
        next.previous = newNode;
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
        return getNode(index).item;
    }

    @Override
    public void set(T value, int index) {
        getNode(index).item = value;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        final T item = node.item;
        final Node next = node.next;
        final Node prev = node.previous;

        if (next == null) {
            last = prev;
        } else {
            next.previous = prev;
        }

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        size--;
        node = null;
        return item;
    }

    @Override
    public T remove(T t) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.item.equals(t)) {
                return remove(i);
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void linkFirst(T value) {
        Node oldFirst = first;
        first = new Node(value, oldFirst, null);
        if (oldFirst != null) {
            oldFirst.previous = first;
        } else {
            last = first;
        }
        size++;
    }

    private void checkPositionIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkElementIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);
        Node currentNode = null;
        if (index < size / 2) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
            return currentNode;
        }
    }

    private static class Node<T> {
        private T item;
        private Node next;
        private Node previous;

        public Node(T item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }
}
