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
        checkPositionForAdd(index);
        if (index == size) {
            addToTail(value);
        } else {
            insertBefore(value, node(index));
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
        checkPositionForGetAndSet(index);
        return node(index).elementValue;
    }

    @Override
    public T remove(int index) {
        checkPositionForGetAndSet(index);
        return unlinkNode(node(index));
    }

    @Override
    public boolean remove(T value) {
        if (value == null) {
            for (Node<T> nodeForRemove = head;
                    nodeForRemove != null; nodeForRemove = nodeForRemove.next) {
                if (nodeForRemove.elementValue == null) {
                    unlinkNode(nodeForRemove);
                    return true;
                }
            }
        } else {
            for (Node<T> nodeForRemove = head;
                    nodeForRemove != null; nodeForRemove = nodeForRemove.next) {
                if (value.equals(nodeForRemove.elementValue)) {
                    unlinkNode(nodeForRemove);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T set(T value, int index) {
        checkPositionForGetAndSet(index);
        Node<T> nodeForChangeValue = node(index);
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

    private void checkPositionForGetAndSet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(MESSAGE_INDEX_ERROR);
        }
    }

    private void checkPositionForAdd(int index) {
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

    private Node<T> node(int index) {
        Node<T> searchNode;
        if (index < (size / 2) + 1) {
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
