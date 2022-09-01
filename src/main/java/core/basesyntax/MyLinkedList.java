package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            linkFirst(value);
            return;
        } else if (index == size) {
            linkLast(value);
            return;
        } else {
            linkedBefore(findNode(index), value);
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
        if (index >= size) {
            outOfBound(index);
        }
        return (T) findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size) {
            outOfBound(index);
        }
        Node current = findNode(index);
        T oldItem = (T) current.item;
        current.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        if (index >= size) {
            outOfBound(index);
        }
        Node current = findNode(index);
        unlink(current);
        return (T) current.item;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        while (current != null) {
            if (Objects.equals(object, current.item)) {
                unlink(current);
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void linkFirst(T value) {
        Node oldHead = head;
        Node newNode = new Node<>(null, value, oldHead);
        head = newNode;
        if (oldHead == null) {
            tail = newNode;
        } else {
            oldHead.prev = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node oldTail = tail;
        Node newNode = new Node(oldTail, value, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    private void linkedBefore(Node current, T value) {
        Node newNode = new Node<>(current.prev, value, current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    private void unlink(Node current) {
        if (size == 1) {
            head = null;
            tail = null;
        }
        if (current.prev == null) {
            unlinkFirst(current);
        } else if (current.next == null) {
            unlinkLast(current);
        } else {
            unlinkCurrent(current);
        }
    }

    private void unlinkCurrent(Node current) {
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
    }

    private void unlinkFirst(Node current) {
        if (current.next != null) {
            head = current.next;
            current.next.prev = null;
        } else {
            head = null;
        }
        size--;
    }

    private void unlinkLast(Node current) {
        tail = current.prev;
        current.prev.next = null;
        size--;
    }

    private void checkIndex(int index) {
        if (index > size) {
            outOfBound(index);
        }
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void outOfBound(int index) {
        throw new ArrayIndexOutOfBoundsException("Out of bounds exception with index: "
                + index);
    }

    private Node findNode(int index) {
        checkIndex(index);
        if (index > size / 2) {
            Node current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        } else {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }
}
