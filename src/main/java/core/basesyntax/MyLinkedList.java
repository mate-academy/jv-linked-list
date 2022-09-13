package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> start;
    private Node<T> end;
    private int size = 0;

    @Override
    public void add(T value) {
        addToTail(value);
    }

    @Override
    public void add(T value, int index) {
        checkBounds(index);
        if (index == size) {
            addToTail(value);
        } else {
            addWithShift(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (final T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNode(index);
        if (node.prev == null && node.next == null) {
            start = null;
            end = null;
        } else if (node.prev == null) {
            start = node.next;
            start.prev = null;
        } else if (node.next == null) {
            end = node.prev;
            end.next = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = start;
        for (int i = 0; i < size; i++) {
            if ((object == null && object == node.value) || node.value.equals(object)) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private Node<T> findNode(int index) {
        checkBounds(index);
        Node<T> node;
        if (index > size >>> 1) {
            node = end;
            for (int i = size - 1; i >= 0; i--) {
                if (index == i) {
                    break;
                }
                node = node.prev;
            }
        }
        node = start;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                break;
            }
            node = node.next;
        }
        return node;
    }

    private void checkBounds(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of Bounds");
        }
    }

    private void checkIndex(int index) {
        checkBounds(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of Bounds");
        }
    }

    private void addToTail(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(value, null, null);
            start = newNode;
        } else {
            Node<T> lastNode = end;
            newNode = new Node<>(value, null, lastNode);
            lastNode.next = newNode;
        }
        end = newNode;
        size++;
    }

    private void addWithShift(T value, int index) {
        Node<T> node = findNode(index);
        Node<T> newNode;
        if (node.prev == null) {
            newNode = new Node<>(value, node, null);
            node.prev = newNode;
            start = newNode;
        } else {
            newNode = new Node<>(value, node, node.prev);
            node.prev.next = newNode;
            node.prev = newNode;
        }
        size++;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, final Node<T> next, final Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
