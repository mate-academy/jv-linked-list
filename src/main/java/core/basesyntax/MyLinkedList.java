package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final String NO_INDEX_MESSAGE = "no index of value: ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (head == null && tail == null) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            newNode.prev.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(NO_INDEX_MESSAGE + index);
        }
        Node<T> newNode;
        Node<T> nextNode;
        Node<T> prevNode;
        if (index == 0 && size == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            nextNode = getNodeByIndex(index);
            newNode = new Node<>(null, value, nextNode);
            nextNode.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            nextNode = getNodeByIndex(index);
            prevNode = nextNode.prev;
            newNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = newNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexExists(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexExists(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        boolean foundObject = false;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == null && object == null) {
                foundObject = true;
                break;
            } else if (currentNode.value != null && currentNode.value.equals(object)) {
                foundObject = true;
                break;
            }
            currentNode = currentNode.next;
        }
        if (foundObject) {
            unlink(currentNode);
        }
        return foundObject;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIfIndexExists(index);
        boolean inFirstHalf = index < size / 2;
        Node<T> currentNode;
        if (inFirstHalf) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = 0; i < size - index - 1; i++) {
                currentNode = currentNode.prev;
            }
        }

        return currentNode;
    }

    private void checkIfIndexExists(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(NO_INDEX_MESSAGE + index);
        }
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode;
        Node<T> prevNode;

        if (node.prev == null && node.next == null) {
            head = null;
            tail = null;
        } else if (node.prev == null && node.next != null) {
            nextNode = head.next;
            nextNode.prev = null;
            head = nextNode;
        } else if (node.next == null) {
            prevNode = tail.prev;
            prevNode.next = null;
            tail = prevNode;
        } else {
            prevNode = node.prev;
            nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        node.next = null;
        node.prev = null;
        size--;
        return node.value;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

}
