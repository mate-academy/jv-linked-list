package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

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
        Objects.checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
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
        if (list.size() == 0) {
            throw new NullPointerException("This list is empty" + list);
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> newNode = getNodeByIndex(index);
        T oldElement = newNode.element;
        newNode.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T removedElement = getRemovedElement(index);
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            Node<T> current = head;
            for (int i = 0; i < size; i++) {
                if ((current.element != null && current.element.equals(object))
                        || (current.element == null && object == null)) {
                    remove(i);
                    return true;
                }
                current = current.next;
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
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
}
