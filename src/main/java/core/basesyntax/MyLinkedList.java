package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
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
        if (index == size) {
            add(value);
            return;
        }
        checkIndexException(index);
        Node<T> prevNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(prevNode.prev, value, prevNode);
        if (prevNode.prev == null) {
            head = newNode;
        } else {
            prevNode.prev.next = newNode;
        }
        prevNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listValue : list) {
            add(listValue);
        }
    }

    @Override
    public T get(int index) {
        checkIndexException(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexException(index);
        T oldValue = findNodeByIndex(index).value;
        findNodeByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexException(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> temporaryNode = head;
        for (int i = 0; i < size; i++) {
            if (temporaryNode.value == object
                    || object != null && object.equals(temporaryNode.value)) {
                unlink(temporaryNode);
                return true;
            }
            temporaryNode = temporaryNode.next;
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
        Node<T> current;
        if (index < (size >> 1)) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndexException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is invalid");
        }
    }

    private T unlink(Node<T> removedNode) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (removedNode.equals(head)) {
            head.next.prev = null;
            head = removedNode.next;
        } else if (removedNode.equals(tail)) {
            tail.prev.next = null;
            tail = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
            removedNode.prev.next = removedNode.next;
        }
        size--;
        return removedNode.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
