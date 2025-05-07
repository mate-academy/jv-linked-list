package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_EXCEPTION = "Index is invalid";
    private static final int SIZE_DEGREE = 2;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (isEmpty()) {
            newNode = new Node<>(null, value, null);
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
        }
        tail = newNode;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            checkIndex(index);
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> prevNode = currentNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, currentNode);
            currentNode.prev = newNode;
            prevNode.next = newNode;
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
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T previousValue = currentNode.element;
        currentNode.element = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> thisNode = head;
        for (int i = 0; i < size; i++) {
            if (thisNode.element == object
                    || object != null && object.equals(thisNode.element)) {
                unlink(thisNode);
                return true;
            }
            thisNode = thisNode.next;
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

    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
            size++;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_EXCEPTION);
        }
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return node.element;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        if (index >= size * SIZE_DEGREE) {
            currentNode = tail;
            for (int i = index; i < size - 1; i++) {
                currentNode = currentNode.prev;
            }
        } else {
            for (int i = index; i > 0; i--) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }
}
