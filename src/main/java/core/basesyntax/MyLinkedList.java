package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE_OF_ADD_VALUE_EXCEPTION = "can't add a value into "
            + "this index because length is ";
    private static final String MESSAGE_OF_GET_VALUE_EXCEPTION = "can't get a value into "
            + "this index because length is ";
    private static final String MESSAGE_OF_SET_VALUE_EXCEPTION = "can't set a value into "
            + "this index because length is ";
    private static final String MESSAGE_OF_REMOVE_VALUE_EXCEPTION = "can't remove a value in "
            + "this index because length is ";
    private static final int ACCURACY_OF_ONE = 1;
    private static final int EMPTY = 0;
    private static final int HEAD_INDEX = 0;
    private Node head;
    private Node tail;
    private int usedSpace;

    @Override
    public void add(T value) {
        if (usedSpace == EMPTY) {
            head = new Node(null, value, null);
            tail = head;
            usedSpace++;
            return;
        }
        tail = new Node(tail, value, null);
        tail.prev.next = tail;
        usedSpace++;
    }

    @Override
    public void add(T value, int index) {
        checkIfIndexOutOfBoundsException(MESSAGE_OF_ADD_VALUE_EXCEPTION, index);
        if (usedSpace == EMPTY) {
            head = new Node(null, value, null);
            tail = head;
            usedSpace++;
            return;
        }
        if (index == HEAD_INDEX) {
            head = new Node(null, value, head);
            head.next.prev = head;
            usedSpace++;
            return;
        }
        if (usedSpace == index) {
            tail = new Node(tail, value, null);
            tail.prev.next = tail;
            usedSpace++;
            return;
        }
        Node targerNode = findNodeByIndex(index - ACCURACY_OF_ONE);
        Node newNode = new Node(targerNode, value, targerNode.next);
        newNode.prev.next = newNode;
        usedSpace++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            if (usedSpace == EMPTY) {
                head = new Node(null, value, null);
                tail = head;
                usedSpace++;
                return;
            }
            tail = new Node(tail,value,null);
            tail.prev.next = tail;
            usedSpace++;
        }
    }

    @Override
    public T get(int index) {
        checkIfIndexOutOfBoundsException(MESSAGE_OF_GET_VALUE_EXCEPTION,
                index < HEAD_INDEX ? index : index + ACCURACY_OF_ONE);
        Node targetNode = findNodeByIndex(index);
        return (T) targetNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexOutOfBoundsException(MESSAGE_OF_SET_VALUE_EXCEPTION,
                index < HEAD_INDEX ? index : index + ACCURACY_OF_ONE);
        Node targetNode = findNodeByIndex(index);
        T targetNodeValue = (T) targetNode.value;
        targetNode.value = value;
        return targetNodeValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexOutOfBoundsException(MESSAGE_OF_REMOVE_VALUE_EXCEPTION,
                index < HEAD_INDEX ? index : index + ACCURACY_OF_ONE);
        Node targetNode = findNodeByIndex(index);
        if (index == HEAD_INDEX) {
            head = targetNode.next;
        }
        if (index == usedSpace) {
            tail = targetNode.prev;
        }
        unlink(targetNode);
        usedSpace--;
        return (T) targetNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        for (int i = 0; i < usedSpace; i++) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                if (i == HEAD_INDEX) {
                    head = currentNode.next;
                }
                unlink(currentNode);
                usedSpace--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return usedSpace;
    }

    @Override
    public boolean isEmpty() {
        return usedSpace == EMPTY;
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
        int halfOfUsedSpace = (int) Math.ceil((double) usedSpace / 2);
        if (index <= halfOfUsedSpace) {
            Node currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node currentNode = tail;
            for (int i = usedSpace - ACCURACY_OF_ONE; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private void checkIfIndexOutOfBoundsException(String message, int index) {
        if (index > usedSpace || index < HEAD_INDEX) {
            throw new IndexOutOfBoundsException(message + (usedSpace - ACCURACY_OF_ONE));
        }
    }

    public class Node<V> {
        private V value;
        private Node<V> next;
        private Node<V> prev;

        private Node(Node<V> prev, V value, Node<V> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

}
