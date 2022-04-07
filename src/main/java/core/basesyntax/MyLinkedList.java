package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        Node(Node<T> previous, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        verifyIndex(index);
        Node<T> current = findNode(index);
        Node<T> predecessor = current.previous;
        Node<T> newNode = new Node<>(predecessor, value, current);
        current.previous = newNode;
        if (predecessor == null) {
            head = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        verifyIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        verifyIndex(index);
        Node<T> replacedNode = findNode(index);
        T oldValue = replacedNode.item;
        replacedNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        verifyIndex(index);
        Node<T> removedNode = findNode(index);
        unlink(removedNode);
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> target = head; target != null; target = target.next) {
            if (object == target.item || object != null && object.equals(target.item)) {
                unlink(target);
                return true;
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

    private void verifyIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void linkFirst(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.previous = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last != null) {
            last.next = newNode;
        }
        size++;
    }

    private void unlink(Node<T> deletedNode) {
        Node<T> next = deletedNode.next;
        Node<T> prev = deletedNode.previous;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            deletedNode.previous = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
            deletedNode.next = null;
        }
        size--;
    }

    private Node<T> findNode(int index) {
        Node<T> target;
        if (index < (size >> 1)) {
            target = head;
            for (int i = 0; i < index; i++) {
                target = target.next;
            }
        } else {
            target = tail;
            for (int i = size - 1; i > index; i--) {
                target = target.previous;
            }
        }
        return target;
    }
}
