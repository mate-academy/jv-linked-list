package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String OUT_OF_BOUND_MESSAGE = "out of bounds for index ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private final T value;

        private Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(OUT_OF_BOUND_MESSAGE + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head = new Node<>(null, value, head);
            head.next.previous = head;
        } else {
            Node<T> indexNode = getNode(index);
            indexNode.previous.next = new Node<>(indexNode.previous, value, indexNode);
            indexNode.previous = indexNode.previous.next;
        }
        size++;
    }

    private void addFirst(T value) {
        head = new Node<>(null, value, null);
        tail = head;
    }

    private void addLast(T value) {
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
    }

    private Node<T> getNode(int index) {
        int halfSize = size >> 1;
        Node<T> resultNode;
        if (halfSize > index) {
            resultNode = head;
            for (int i = 0; i < index; i++) {
                resultNode = resultNode.next;
            }
        } else {
            resultNode = tail;
            for (int i = 0; i < size - 1 - index; i++) {
                resultNode = resultNode.previous;
            }
        }
        return resultNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> resultNode = getNode(index);
        return resultNode.value;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(OUT_OF_BOUND_MESSAGE + index);
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> indexNode = getNode(index);
        if (index == 0) {
            head = new Node<>(null, value, indexNode.next);
            indexNode.next.previous = head;
        } else if (index == size - 1) {
            tail = new Node<>(indexNode.previous, value, null);
            indexNode.previous.next = tail;
        } else {
            indexNode.previous.next = new Node<>(indexNode.previous, value, indexNode.next);
            indexNode.next.previous = indexNode.previous.next;
        }
        return indexNode.value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    private T unlink(Node<T> node) {
        T removedValue = null;
        if (head == tail) {
            removedValue = head.value;
            head = null;
            tail = null;
        } else if (node.previous == null) {
            removedValue = head.value;
            head = head.next;
            head.previous = null;
        } else if (node.next == null) {
            removedValue = tail.value;
            tail = tail.previous;
            tail.next = null;
        } else {
            removedValue = node.value;
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        boolean isDeleted = false;
        for (int i = 0; i < size; i++) {
            Node<T> indexNode = getNode(i);
            if (object == null ? indexNode.value == null : object.equals(indexNode.value)) {
                unlink(indexNode);
                isDeleted = true;
                break;
            }
        }
        return isDeleted;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
