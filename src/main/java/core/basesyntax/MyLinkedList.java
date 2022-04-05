package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

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
        if (index == size && size == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            linkLast(value);
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
        for (T position: list) {
            add(position);
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
        Node<T> sett = findNode(index);
        T oldValue = sett.item;
        sett.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        verifyIndex(index);
        Node<T> removedNode = findNode(index);
        unlink(findNode(index));
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> target = head; target != null; target = target.next) {
                if (target.item == null) {
                    unlink(target);
                    return true;
                }
            }
        } else {
            for (Node<T> target = head; target != null; target = target.next) {
                if (object.equals(target.item)) {
                    unlink(target);
                    return true;
                }
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

    private void linkFirst(T e) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(null, e, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.previous = newNode;
        }
        size++;
    }

    void linkLast(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last != null) {
            last.next = newNode;
        }
        size++;
    }

    void unlink(Node<T> deletedNode) {
        final Node<T> next = deletedNode.next;
        final Node<T> prev = deletedNode.previous;

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

    Node<T> findNode(int index) {
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
