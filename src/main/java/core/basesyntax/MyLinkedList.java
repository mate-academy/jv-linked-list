package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        linkToTheEnd(value);
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            linkToTheEnd(value);
        } else {
            linkByIndex(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        T oldValue = nodeToRemove.value;
        unlinkNode(nodeToRemove);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = head; i != null; i = i.next) {
                if (i.value == null) {
                    unlinkNode(i);
                    return true;
                }
            }
        } else {
            for (Node<T> i = head; i != null; i = i.next) {
                if (object.equals(i.value)) {
                    unlinkNode(i);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bound");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index > size / 2) {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }

    private void linkToTheEnd(T value) {
        Node<T> previous = tail;
        Node<T> newOne = new Node<>(previous, value, null);
        tail = newOne;
        if (previous == null) {
            head = newOne;
        } else {
            previous.next = newOne;
        }
        size++;
    }

    private void linkByIndex(T value, int index) {
        Node<T> next = getNode(index);
        Node<T> previous = next.prev;
        Node<T> newOne = new Node<>(previous, value, next);
        next.prev = newOne;
        if (previous == null) {
            head = newOne;
        } else {
            previous.next = newOne;
        }
        size++;
    }

    private void unlinkNode(Node<T> nodeToUnlink) {
        Node<T> previous = nodeToUnlink.prev;
        Node<T> next = nodeToUnlink.next;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            nodeToUnlink.prev = null;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
            nodeToUnlink.next = null;
        }

        nodeToUnlink.value = null;
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}


