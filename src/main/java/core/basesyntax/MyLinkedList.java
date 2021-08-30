package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_EXCEPTION = "Index is invalid";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.element = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size > 0) {
            tail.next = newNode;
        } else {
            head = newNode;
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
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T previousNode = currentNode.element;
        currentNode.element = value;
        return previousNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> thisNode = getNodeByValue(object);
        if (thisNode != null) {
            unlink(thisNode);
            return true;
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
        if (index >= size / 2) {
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

    private Node<T> getNodeByValue(T value) {
        for (int i = 0; i < size; i++) {
            if (getNodeByIndex(i).element == value
                    || value != null && value.equals(getNodeByIndex(i).element)) {
                return getNodeByIndex(i);
            }
        }
        return null;
    }
}
