package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head = newNode;
            head.next.prev = newNode;
        } else {
            Node<T> nodeOfIndex = getNodeOfIndex(index);
            Node<T> newNode = new Node<>(nodeOfIndex.prev, value, nodeOfIndex);
            nodeOfIndex.prev.next = newNode;
            nodeOfIndex.prev = newNode;
        }
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
        return getNodeOfIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        if (checkIndex(index)) {
            T oldValue = getNodeOfIndex(index).element;
            getNodeOfIndex(index).element = value;
            return oldValue;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            T removedElement = getNodeOfIndex(index).element;
            unlink(getNodeOfIndex(index));
            size--;
            return removedElement;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean remove(T object) {
        try {
            remove(getIndexOfElement(object));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNodeOfIndex(int index) {
        if (checkIndex(index)) {
            if (index <= size / 2) {
                Node<T> node = head;
                for (int i = 0; i < index; i++) {
                    node = node.next;
                }
                return node;
            } else if (index > size / 2) {
                Node<T> node = tail;
                for (int i = size - 1; i > index; i--) {
                    node = node.prev;
                }
                return node;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    private int getIndexOfElement(T object) throws NoSuchElementException {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.element == object || node.element.equals(object)) {
                return i;
            }
            node = node.next;
        }
        throw new NoSuchElementException();
    }

    private void unlink(Node<T> node) {
        if (node == head && size == 1) {
            node.next = null;
        } else if (node == head && size > 1) {
            head = head.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }
}
