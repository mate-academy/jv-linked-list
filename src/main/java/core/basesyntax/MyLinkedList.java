package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.item = item;
        }

        public Node(Node<T> next, T item, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
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
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.next.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = takeElementByIndex(index);
            newNode.next = current;
            current.prev.next = newNode;
            newNode.prev = current.prev;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T values : list) {
            add(values);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> elementByIndex = takeElementByIndex(index);
        return (elementByIndex == null) ? null : elementByIndex.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> elementByIndex = takeElementByIndex(index);
        if (elementByIndex == null) {
            return null;
        }
        T valueToReturn = elementByIndex.item;
        elementByIndex.item = value;
        return valueToReturn;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> elementByIndex = removeElement(index);
        return elementByIndex.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int index = 0; index < size; index++) {
            if (current == null) {
                return false;
            } else if (Objects.equals(object, current.item)) {
                removeElement(index);
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
        return (head == null);
    }

    private Node<T> takeElementByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                return null;
            } else {
                current = current.next;
            }
        }
        return current;
    }

    private Node<T> removeElement(int index) {
        Node<T> elementByIndexWhichDelete = takeElementByIndex(index);
        if (index == 0) {
            head = elementByIndexWhichDelete.next;
            if (head == null) {
                tail = null;
            }
        } else if (index == size - 1) {
            tail = tail.prev;
        } else {
            elementByIndexWhichDelete.next.prev = elementByIndexWhichDelete.prev;
            elementByIndexWhichDelete.prev.next = elementByIndexWhichDelete.next;
        }
        size--;
        return elementByIndexWhichDelete;//removedElement;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index" + index + "out of size" + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index" + index + "out of size" + size);
        }
    }
}
