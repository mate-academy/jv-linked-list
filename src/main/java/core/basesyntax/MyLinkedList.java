package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newElement = new Node<>(null, value, null);
        if (size == 0) {
            head = newElement;
        } else {
            newElement.prev = tail;
            tail.next = newElement;
        }
        tail = newElement;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nextElement = findElementByIndex(index);
        Node<T> addedElement = new Node<>(nextElement.prev, value, nextElement);
        if (nextElement.prev != null) {
            nextElement.prev.next = addedElement;
        } else {
            head = addedElement;
        }
        nextElement.prev = addedElement;
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
        return findElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setElement = findElementByIndex(index);
        T currentSetElement = setElement.value;
        setElement.value = value;
        return currentSetElement;
    }

    @Override
    public T remove(int index) {
        Node<T> removedElement = findElementByIndex(index);
        return unlink(removedElement);
    }

    @Override
    public boolean remove(T object) {
        Node<T> element = head;
        for (int i = 0; i < size; i++) {
            if (element.value == object || element.value != null && element.value.equals(object)) {
                unlink(element);
                return true;
            }
            element = element.next;
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

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    private void checkPositionIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: "
                    + index + " is out of bounds this list size: " + size);
        }
    }

    private Node<T> findElementByIndex(int index) {
        checkPositionIndex(index);
        Node<T> nextElementByIndex;
        if (index < size / 2) {
            nextElementByIndex = head;
            int counter = 0;
            while (counter < index) {
                nextElementByIndex = nextElementByIndex.next;
                counter++;
            }
        } else {
            nextElementByIndex = tail;
            int counter = size - 1;
            while (counter > index) {
                nextElementByIndex = nextElementByIndex.prev;
                counter--;
            }
        }
        return nextElementByIndex;
    }

    private T unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node.value;
    }
}
