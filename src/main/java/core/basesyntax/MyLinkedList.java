package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private Node<T> left;
        private Node<T> right;
        private T value;

        Node(Node<T> left, T value, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedElement = new Node<>(null, value, null);
        if (isEmpty()) {
            first = addedElement;
            last = addedElement;
        } else {
            addedElement.left = last;
            last.right = addedElement;
            last = addedElement;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of List bounds");
        }
        if (index == size()) {
            add(value);
            return;
        }
        Node<T> nextElement = first;
        int i = 0;
        while (i < index) {
            nextElement = nextElement.right;
            i++;
        }
        Node<T> addedElement = new Node<>(nextElement.left, value, nextElement);
        if (nextElement.left != null) {
            nextElement.left.right = addedElement;
        } else {
            first = addedElement;
        }
        nextElement.left = addedElement;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        isOutOfBounds(index);
        Node<T> currentElement = first;
        int i = 0;
        while (i < index) {
            currentElement = currentElement.right;
            i++;
        }
        return currentElement.value;
    }

    @Override
    public T set(T value, int index) {
        isOutOfBounds(index);
        Node<T> currentElement = first;
        int i = 0;
        while (i < index) {
            currentElement = currentElement.right;
            i++;
        }
        T changedValue = currentElement.value;
        currentElement.value = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        isOutOfBounds(index);
        T deletedValue;
        if (size == 1) {
            size = 0;
            return first.value;
        }
        if (index == size - 1) {
            deletedValue = last.value;
            last = last.left;
            last.right = null;
            size--;
            return deletedValue;
        }
        if (index == 0) {
            deletedValue = first.value;
            first = first.right;
            first.left = null;
            size--;
            return deletedValue;
        }
        int i = 0;
        Node<T> currentElement = first;
        while (i < index) {
            currentElement = currentElement.right;
            i++;
        }
        currentElement.left.right = currentElement.right;
        currentElement.right.left = currentElement.left;
        size--;
        return currentElement.value;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        Node<T> currentElement = first;
        while (currentElement != null && !(object == null && currentElement.value == null
                || object != null && object.equals(currentElement.value))) {
            currentElement = currentElement.right;
            i++;
        }
        if (i < size) {
            remove(i);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void isOutOfBounds(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of List bounds");
        }
    }
}
