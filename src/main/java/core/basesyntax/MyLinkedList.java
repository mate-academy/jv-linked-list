package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE_ADD_EXCEPTION = "can't add a value into "
            + "this index because length is ";
    private static final String MESSAGE_GET_EXCEPTION = "can't get a value into "
            + "this index because length is ";
    private static final String MESSAGE_SET_EXCEPTION = "can't set a value into "
            + "this index because length is ";
    private static final String MESSAGE_REMOVE_EXCEPTION = "can't remove a value in "
            + "this index because length is ";
    private static final int ACCURACY_OF_ONE = 1;
    private static final int EMPTY = 0;
    private static final int HEAD_INDEX = 0;
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == EMPTY) {
            head = new Node(null, value, null);
            tail = head;
            size++;
            return;
        }
        tail = new Node(tail, value, null);
        tail.prev.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIfIndexOutOfBoundsException(MESSAGE_ADD_EXCEPTION, index);
        if (size == EMPTY) {
            head = new Node(null, value, null);
            tail = head;
            size++;
            return;
        }
        if (index == HEAD_INDEX) {
            head = new Node(null, value, head);
            head.next.prev = head;
            size++;
            return;
        }
        if (size == index) {
            tail = new Node(tail, value, null);
            tail.prev.next = tail;
            size++;
            return;
        }
        Node targerNode = findNodeByIndex(index - ACCURACY_OF_ONE);
        Node newNode = new Node(targerNode, value, targerNode.next);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
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
        checkIfIndexOutOfBoundsException(MESSAGE_GET_EXCEPTION,
                index < HEAD_INDEX ? index : index + ACCURACY_OF_ONE);
        return (T) findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexOutOfBoundsException(MESSAGE_SET_EXCEPTION,
                index < HEAD_INDEX ? index : index + ACCURACY_OF_ONE);
        Node targetNode = findNodeByIndex(index);
        T targetNodeValue = (T) targetNode.value;
        targetNode.value = value;
        return targetNodeValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexOutOfBoundsException(MESSAGE_REMOVE_EXCEPTION,
                index < HEAD_INDEX ? index : index + ACCURACY_OF_ONE);
        Node targetNode = findNodeByIndex(index);
        if (index == HEAD_INDEX) {
            head = targetNode.next;
        }
        if (index == size) {
            tail = targetNode.prev;
        }
        unlink(targetNode);
        size--;
        return (T) targetNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == object
                    || current.value != null && current.value.equals(object)) {
                if (i == HEAD_INDEX) {
                    head = current.next;
                }
                if (i == size - ACCURACY_OF_ONE) {
                    tail = current.prev;
                }
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == EMPTY;
    }

    private void unlink(Node node) {
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
    }

    private Node findNodeByIndex(int index) {
        int halfOfUsedSpace = (int) Math.ceil((double) size / 2);
        Node current = head;
        if (index <= halfOfUsedSpace) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - ACCURACY_OF_ONE; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIfIndexOutOfBoundsException(String message, int index) {
        if (index > size || index < HEAD_INDEX) {
            throw new IndexOutOfBoundsException(message + (size - ACCURACY_OF_ONE));
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
