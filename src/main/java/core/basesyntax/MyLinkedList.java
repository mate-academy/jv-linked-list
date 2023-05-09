package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
            return;
        }
        checkPositionIndex(index);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index - 1);
            newNode.next = nodeByIndex.next;
            nodeByIndex.next.prev = newNode;
            nodeByIndex.next = newNode;
            newNode.prev = nodeByIndex;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("This list is empty" + list);
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        T oldElement = newNode.element;
        newNode.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return getRemovedElement(index);
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element == object || current.element != null
                    && current.element.equals(object)) {
                remove(i);
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

    private Node<T> getNodeByIndex(int index) {
        checkPositionIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T getRemovedElement(int index) {
        T removedElement;
        if (index == 0) {
            removedElement = head.element;
            head = head.next;
        } else if (index == size - 1) {
            removedElement = tail.element;
            tail = tail.prev;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index - 1);
            removedElement = nodeByIndex.next.element;
            nodeByIndex.next.next.prev = nodeByIndex;
            nodeByIndex.next = nodeByIndex.next.next;
        }
        size--;
        return removedElement;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is incorrect: " + index);
        }
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }
}
