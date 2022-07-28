package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        final Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
        } else {
            createLinks(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> oldNode = getNode(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> removedNode = getNode(index);
        return unLink(removedNode);
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == null && getNode(i).value == null
                    || getNode(i).value.equals(object)) {
                unLink(getNode(i));
                return true;
            }
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

    private void createLinks(T value, int index) {
        final Node<T> nextNode = getNode(index);
        final Node<T> prevNode = nextNode.prev;
        final Node<T> newNode = new Node<>(prevNode, value, nextNode);
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        nextNode.prev = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> requiredNode;
        if (index < size / 2) {
            requiredNode = head;
            for (int i = 0; i < index; i++) {
                requiredNode = requiredNode.next;
            }
        } else {
            requiredNode = tail;
            for (int i = size - 1; i > index; i--) {
                requiredNode = requiredNode.prev;
            }
        }
        return requiredNode;
    }

    private T unLink(Node<T> node) {
        final T currrentValue = node.value;
        final Node<T> nextNode = node.next;
        final Node<T> prevNode = node.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        node.value = null;
        size--;
        return currrentValue;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index + ", Size: " + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.value = element;
            this.next = next;
        }
    }
}
