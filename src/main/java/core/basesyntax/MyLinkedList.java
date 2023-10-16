package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Provided index is out of valid range";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            addToHead(newNode);
        } else {
            addByIndex(newNode, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T returnValue = node.value;
        node.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        size--;
        T returnValue = nodeToRemove.value;
        nodeToRemove.value = null;
        return returnValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == current.value)
                    || (object != null && object.equals(current.value))) {
                unlink(current);
                current.value = null;
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
        return size == 0;
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size) {
            throwException();
        }
    }

    private Node<T> getNode(int index) {
        Node<T> returnNode = null;
        if (index < 0 || index >= size) {
            throwException();
        } else if (index < size / 2) {
            returnNode = head;
            for (int i = 0; i < index; i++) {
                returnNode = returnNode.next;
            }
        } else {
            returnNode = tail;
            for (int i = size - 1; i > index; i--) {
                returnNode = returnNode.prev;
            }
        }
        return returnNode;
    }

    private void addByIndex(Node<T> node, int index) {
        if (index == size) {
            tail.next = node;
            node.prev = tail;
            tail = node;
        } else {
            Node<T> nodeByIndex = getNode(index);
            node.prev = nodeByIndex.prev;
            nodeByIndex.prev.next = node;
            nodeByIndex.prev = node;
            node.next = nodeByIndex;
        }
    }

    private void addToHead(Node<T> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }
    }

    private void unlink(Node<T> node) {
        if (node.equals(head)) {
            head = head.next;
            node.prev = null;
        } else if (node.equals(tail)) {
            tail = tail.prev;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

    }

    private void throwException() {
        throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.value = item;
            this.next = next;
        }
    }
}
