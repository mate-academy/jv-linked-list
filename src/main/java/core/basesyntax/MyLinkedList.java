package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_ARRAY_MESSAGE = "Index less than 0 or more than size";
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

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
        rangeCheck(index);
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(node.previous, value, node);
        if (node.previous == null) {
            head = newNode;
        } else {
            node.previous.next = newNode;
        }
        node.previous = newNode;
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
        rangeCheck(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        rangeCheck(index);
        Node<T> node = getNode(index);
        T element = node.value;
        node.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        Node<T> node = getNode(index);
        return unlinkAndRemove(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempNode = head;
        for (int i = 0; i < size; i++) {
            T value = tempNode.value;
            if (object == value || value != null && value.equals(object)) {
                unlinkAndRemove(tempNode);
                return true;
            }
            tempNode = tempNode.next;
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

    private Node<T> getNode(int index) {
        int middle = size >> 1;
        Node<T> currentNode;
        if (index <= middle) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(INDEX_OUT_OF_ARRAY_MESSAGE);
        }
    }

    private T unlinkAndRemove(Node<T> node) {
        if (node.previous == null) {
            head = node.next;
            if (node.next == null) {
                tail = null;
            }
        } else if (node.next == null) {
            tail = node.previous;
            tail.next = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        T element = node.value;
        node = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
