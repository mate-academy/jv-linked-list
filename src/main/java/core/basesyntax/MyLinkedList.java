package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value,Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
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
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            final Node<T> currentNode = findNodeByIndex(index);
            final Node<T> pred = currentNode.prev;
            final Node<T> newNode = new Node<>(pred, value, currentNode);
            currentNode.prev = newNode;
            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        T oldValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        final T element = nodeByIndex.value;
        final Node<T> prev = nodeByIndex.prev;
        final Node<T> next = nodeByIndex.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            nodeByIndex.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            nodeByIndex.next = null;
        }
        nodeByIndex.value = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = head; x != null; x = x.next) {
            if ((object == x.value) || (object != null && object.equals(x.value))) {
                if (x.prev == null) {
                    head = x.next;
                } else {
                    x.prev.next = x.next;
                }
                if (x.next == null) {
                    tail = x.prev;
                } else {
                    x.next.prev = x.prev;
                }
                x.value = null;
                size--;
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

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound" + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound" + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        if (index < (size / 2)) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }
}
