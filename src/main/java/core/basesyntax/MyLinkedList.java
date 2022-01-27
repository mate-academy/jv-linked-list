package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
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
            tail = new Node<T>(null, value, null);
            head = tail;
        } else {
            Node<T> newNode = new Node<T>(head, value, null);
            head.next = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode;
        Node<T> nodeOfIndex = getNodeOfIndex(index);
        if (index == 0) {
            newNode = new Node<T>(null, value, nodeOfIndex);
            nodeOfIndex.prev = newNode;
            tail = newNode;
        } else {
            newNode = new Node<T>(nodeOfIndex.prev, value, nodeOfIndex);
            nodeOfIndex.prev.next = newNode;
            nodeOfIndex.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
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

    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
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

    private int getIndexOfElement(T object) throws NoSuchElementException {
        Node<T> node = tail;
        for (int i = 0; i < size; i++) {
            if (node.element == object || node.element.equals(object)) {
                return i;
            }
            node = node.next;
        }
        throw new NoSuchElementException();
    }

    private void unlink(Node<T> node) {
        if (node == tail && size == 1) {
            node.prev = null;
            node.next = null;
            head = null;
            tail = null;
        } else if (node == tail && size > 1) {
            tail = tail.next;
            tail.prev = null;
            node.prev = null;
            node.next = null;
        } else if (node == head) {
            node.prev.next = node.next;
            head = node.prev;
            node.prev = null;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
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
                Node<T> node = tail;
                for (int i = 0; i < index; i++) {
                    node = node.next;
                }
                return node;
            } else if (index > size / 2) {
                Node<T> node = head;
                for (int i = size - 1; i > index; i--) {
                    node = node.prev;
                }
                return node;
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
