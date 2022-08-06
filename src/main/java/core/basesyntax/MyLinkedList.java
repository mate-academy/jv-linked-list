package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String WRONG_INDEX = "The index is out off list.";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) throws LinkedListIndexOutOfBoundsException {
        checkIndex(index);
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> prevNode = currentNode.prev;
        Node<T> insertedNode = new Node<>(prevNode, value, currentNode);
        prevNode.next = insertedNode;
        currentNode.prev = insertedNode;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) throws LinkedListIndexOutOfBoundsException {
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) throws LinkedListIndexOutOfBoundsException {
        checkIndex(index);
        findNodeByIndex(index).value = value;
        return value;
    }

    @Override
    public T remove(int index) throws LinkedListIndexOutOfBoundsException {
        checkIndex(index);
        Node<T> deletedNode = findNodeByIndex(index);
        deletedNode.prev.next = deletedNode.next;
        deletedNode.next.prev = deletedNode.prev;
        size--;
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < 0.5 * size) {
            currentNode = head;
            for (int i = 0; i < index ; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index ; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) throws LinkedListIndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new LinkedListIndexOutOfBoundsException(WRONG_INDEX);
        }
    }

    private static class Node<T> {
        Node<T> prev;
        Node<T> next;
        T value;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
