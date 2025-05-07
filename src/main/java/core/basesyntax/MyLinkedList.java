package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String NEGATIVE_INDEX_MESSAGE = "Index must be positive number";
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index must be positive number";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>((size == 0) ? null : tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAddNode(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nextNode = findNodeByIndex(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        if (prevNode != null && prevNode.next != null) {
            prevNode.next = newNode;
        } else if (prevNode == null || prevNode.prev == null) {
            head = newNode;
        }
        nextNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndexToRemoveNode(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexToRemoveNode(index);
        Node<T> indexNode = findNodeByIndex(index);
        T oldValue = indexNode.value;
        indexNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexToRemoveNode(index);
        Node<T> deletedNode = findNodeByIndex(index);
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            Node<T> prevNode = (index > 0) ? deletedNode.prev : null;
            Node<T> nextNode = (index < size) ? deletedNode.next : null;
            if (index == 0) {
                nextNode.prev = null;
                head = nextNode;
            } else if (index == size - 1) {
                prevNode.next = null;
                tail = prevNode;
            } else {
                prevNode.next = deletedNode.next;
                nextNode.prev = deletedNode.prev;
            }
        }
        size--;
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        int index = 0;
        do {
            if (object == current.value
                    || (current.value != null && current.value.equals(object))) {
                remove(index);
                return true;
            }
            index++;
            current = current.next;
        } while (current.next != null);
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

    private void checkIndexToAddNode(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        } else if (index < 0) {
            throw new IndexOutOfBoundsException(NEGATIVE_INDEX_MESSAGE);
        }
    }

    private void checkIndexToRemoveNode(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        } else if (index < 0) {
            throw new IndexOutOfBoundsException(NEGATIVE_INDEX_MESSAGE);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            int i = 0;
            while (i < index) {
                current = current.next;
                i++;
            }
        } else {
            current = tail;
            int i = size - 1;
            while (i > index) {
                current = current.prev;
                i--;
            }
        }
        return current;
    }

    private class Node<T> {
        private Node<T> next;
        private T value;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
