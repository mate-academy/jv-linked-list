package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE_INDEX_ERROR = "Index outside the size of the list";

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private T elementValue;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T elementValue, Node<T> next) {
            this.previous = previous;
            this.elementValue = elementValue;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        addToTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            addToTail(value);
        } else {
            insertBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            addToTail(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndexForGetAndSet(index);
        return getNodeByIndex(index).elementValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForGetAndSet(index);
        return unlinkNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T value) {
        Node<T> nodeForRemove = head;
        if (nodeForRemove.next == null) {
            unlinkNode(nodeForRemove);
            return true;
        }
        while (nodeForRemove.next != null) {
            if (nodeForRemove.elementValue == null
                    || (nodeForRemove.elementValue != null
                    && nodeForRemove.elementValue.equals(value))) {
                unlinkNode(nodeForRemove);
                return true;
            }
            nodeForRemove = nodeForRemove.next;
        }
        return false;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGetAndSet(index);
        Node<T> nodeForChangeValue = getNodeByIndex(index);
        T oldValue = nodeForChangeValue.elementValue;
        nodeForChangeValue.elementValue = value;
        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addToTail(T value) {
        Node<T> lastElement = tail;
        Node<T> newNode = new Node<>(lastElement, value, null);
        tail = newNode;
        if (lastElement != null) {
            lastElement.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    private void checkIndexForGetAndSet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_ERROR);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_ERROR);
        }
    }

    private void insertBefore(T value, Node<T> node) {
        Node<T> linkToPrevious = node.previous;
        Node<T> insertedNode = new Node<>(linkToPrevious, value, node);
        node.previous = insertedNode;
        if (linkToPrevious == null) {
            head = insertedNode;
        } else {
            linkToPrevious.next = insertedNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> searchNode;
        if (index <= (size / 2)) {
            searchNode = head;
            for (int i = 0; i < index; i++) {
                searchNode = searchNode.next;
            }
        } else {
            searchNode = tail;
            for (int i = size - 1; i > index; i--) {
                searchNode = searchNode.previous;
            }
        }
        return searchNode;
    }

    private T unlinkNode(Node<T> node) {
        final Node<T> nextElement = node.next;
        final Node<T> previousElement = node.previous;
        final T element = node.elementValue;
        if (previousElement == null) {
            head = nextElement;
        } else {
            previousElement.next = nextElement;
            node.previous = null;
        }
        if (nextElement == null) {
            tail = previousElement;
        } else {
            nextElement.previous = previousElement;
            node.next = null;
        }
        size--;
        return element;
    }
}
