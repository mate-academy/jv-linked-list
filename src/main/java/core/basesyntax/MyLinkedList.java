package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            linkFirst(value);
            return true;
        }
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (size == 0 && index == 0) {
            linkFirst(value);
        } else if (size == index) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeToChange = node(index);
        T oldValue = nodeToChange.value;
        nodeToChange.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> checkNode = head;
        while (checkNode != null) {
            if ((object == null && checkNode.value == null)
                    || (checkNode.value != null && checkNode.value.equals(object))) {
                unlink(checkNode);
                return true;
            }
            checkNode = checkNode.next;
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

    private void linkFirst(T value) {
        Node<T> prevHead = head;
        Node<T> newNode = new Node<>(null, value, prevHead);
        head = newNode;
        if (prevHead == null) {
            tail = newNode;
        } else {
            prevHead.prev = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> prevTail = tail;
        Node<T> newNode = new Node<>(prevTail, value,null);
        tail = newNode;
        if (prevTail == null) {
            head = newNode;
        } else {
            prevTail.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> nodeBefore) {
        Node<T> before = nodeBefore.prev;
        Node<T> newNode = new Node<>(before, value, nodeBefore);
        nodeBefore.prev = newNode;
        if (before == null) {
            head = newNode;
        } else {
            before.next = newNode;
        }
        size++;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    public Node<T> node(int index) {
        checkElementIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T unlink(Node<T> removedValue) {
        final T element = removedValue.value;
        Node<T> next = removedValue.next;
        Node<T> prev = removedValue.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            removedValue.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            removedValue.next = null;
        }

        removedValue.value = null;
        size--;
        return element;
    }

    public static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

}
