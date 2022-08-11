package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
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
        Node<T> oldNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(oldNode.previous, value, oldNode);
        if (oldNode.previous == null) {
            head = newNode;
        } else {
            oldNode.previous.next = newNode;
        }
        oldNode.previous = newNode;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldNodeValue = getNodeByIndex(index).item;
        getNodeByIndex(index).item = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = head;
        for (int i = 0; i < size; i++) {
            if (removedNode.item == object
                    || object != null && object.equals(removedNode.item)) {
                unlink(removedNode);
                return true;
            }
            removedNode = removedNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index + " for size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> node;
        if (index <= size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private T unlink(Node<T> removedElement) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (removedElement.equals(head)) {
            head.next.previous = null;
            head = removedElement.next;
        } else if (removedElement.equals(tail)) {
            tail.previous.next = null;
            tail = removedElement.previous;
        } else {
            removedElement.next.previous = removedElement.previous;
            removedElement.previous.next = removedElement.next;
        }
        size--;
        return removedElement.item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.next = next;
            this.item = item;
            this.previous = previous;
        }
    }
}

