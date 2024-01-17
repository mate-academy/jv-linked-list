package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;
    private Node<T> boofNode;
    private Node<T> newNode;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            linkLast(value);
            return;
        }
        isIndexOutOfBounds(index);
        Node<T> oldNode = findeIndex(index);
        newNode = new Node<>(oldNode.prev, value, oldNode);
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T newNode : list) {
            linkLast(newNode);
        }
    }

    @Override
    public T get(int index) {
        isIndexOutOfBounds(index);
        return findeIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        isIndexOutOfBounds(index);
        boofNode = findeIndex(index);
        T oldValue = boofNode.value;
        boofNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexOutOfBounds(index);
        boofNode = findeIndex(index);
        T removedValue = boofNode.value;
        if (index == 0) {
            first = first.next;
        } else if (index == size - 1) {
            last = last.prev;
        } else {
            boofNode.prev.next = boofNode.next;
            boofNode.next.prev = boofNode.prev;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            boofNode = findeIndex(i);
            if (object == boofNode.value || object != null && object.equals(boofNode.value)) {
                remove(i);
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

    private Node<T> findeIndex(int index) {
        int halfSize = size / 2;
        if (index < halfSize) {
            boofNode = first;
            int count = 0;
            while (count < index) {
                boofNode = boofNode.next;
                count++;
            }
            return boofNode;
        } else {
            boofNode = last;
            int count = size - 1;
            while (count > index) {
                boofNode = boofNode.prev;
                count--;
            }
            return boofNode;
        }
    }

    private void linkFirst(T value) {
        Node<T> first = this.first;
        newNode = new Node<>(null, value, first);
        this.first = newNode;
        if (first == null) {
            last = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Node<T> last = this.last;
        newNode = new Node<>(last, value, null);
        this.last = newNode;
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private boolean isIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        } else {
            return true;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}

