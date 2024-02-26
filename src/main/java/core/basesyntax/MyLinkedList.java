package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<>(prevNode, value, null);
        tail = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of bounds " + size + "!");
        }

        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (currentNode.prev == null) {
                head = newNode;
            } else {
                currentNode.prev.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T listItem : list) {
            add(listItem);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = getNode(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (areValuesEqual(object, currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> getNode(int index) {
        Node<T> tempNode = head;
        if (index < (size / 2)) {
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
        } else {
            for (int i = index; i > 0; i--) {
                tempNode = tempNode.next;
            }
        }
        return tempNode;
    }

    private boolean areValuesEqual(T value, T otherValue) {
        return ((value == otherValue)
                || (value != null && value.equals(otherValue)));
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of bounds " + size + "!");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
