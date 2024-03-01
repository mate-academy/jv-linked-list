package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node head;
    private Node tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node(null, value, null);
            tail = head;
        } else if (size > 0) {
            Node lastNode = tail;
            tail = new Node(lastNode, value, null);
            lastNode.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size >= 0 && index == size) {
            add(value);
        } else {
            Node currentNode = findNodeByIndex(index);
            if (index == 0) {
                head = new Node(currentNode.prev, value, currentNode);
                size++;
                return;
            }
            Node previousNod = currentNode.prev;
            currentNode.prev = new Node(currentNode.prev, value, currentNode);
            previousNod.next = currentNode.prev;
            size++;
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
        Node node = findNodeByIndex(index);
        return (T) node.value;
    }

    @Override
    public T set(T value, int index) {
        Node node = findNodeByIndex(index);
        T oldValue = (T) node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node nodeToRemove = findNodeByIndex(index);
        T value = (T) nodeToRemove.value;
        unlink(nodeToRemove);
        return value;
    }

    @Override
    public boolean remove(T valueToRemove) {
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (areValuesEqual(valueToRemove, (T) current.value)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private boolean isaBoolean(T object, Node node) {
        return (object == null && node.value == null)
                || (node.value != null && node.value.equals(object));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {

        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }

    }

    private boolean areValuesEqual(T value1, T value2) {
        return (value1 == null && value2 == null)
                || (value1 != null && value1.equals(value2));
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node findNodeByIndex(int index) {
        checkIndex(index);
        if (size / 2 > index) {
            return findFromHead(index);
        } else {
            return findFromTail(index);
        }
    }

    private Node findFromHead(int index) {
        Node currentNode = head;
        for (int i = 0; i != index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node findFromTail(int index) {
        Node currentNode = tail;
        for (int i = size - 1; i != index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void unlink(Node node) {
        if (head == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            Node nextNode = node.next;
            nextNode.prev = null;
            node.next = null;
            head = nextNode;
        } else if (node == tail) {
            Node previousNode = node.prev;
            previousNode.next = null;
            node.prev = null;
            tail = previousNode;
        } else {
            Node previousNode = node.prev;
            Node nextNode = node.next;
            previousNode.next = nextNode;
            nextNode.prev = previousNode;
            node.next = null;
            node.prev = null;
        }
        size--;

    }
}

