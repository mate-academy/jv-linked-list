package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String OUT_OF_BOUND_MESSAGE = "out of bounds for index ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

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

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> indexNode = getNode(index);
        T oldValue = indexNode.value;
        indexNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
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

    private void addFirst(T value) {
        head = new Node<>(null, value, null);
        tail = head;
    }

    private void addLast(T value) {
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(OUT_OF_BOUND_MESSAGE + index);
        }
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.previous;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.previous = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.previous = prev;
            node.next = null;
        }
        T removedValue = node.value;
        node.value = null;
        size--;
        return removedValue;
    }

    private class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private T value;

        private Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
