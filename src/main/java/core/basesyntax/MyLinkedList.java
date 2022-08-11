package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedElement = new Node<>(null, value, null);
        if (isEmpty()) {
            first = addedElement;
            last = addedElement;
        } else {
            addedElement.prev = last;
            last.next = addedElement;
            last = addedElement;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                   + " out of List bounds with size " + size);
        }
        if (index == size()) {
            add(value);
            return;
        }
        Node<T> nextElement = findElement(index);
        Node<T> addedElement = new Node<>(nextElement.prev, value, nextElement);
        if (nextElement.prev != null) {
            nextElement.prev.next = addedElement;
        } else {
            first = addedElement;
        }
        nextElement.prev = addedElement;
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
        checkIndex(index);
        Node<T> currentElement = findElement(index);
        return currentElement.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentElement = findElement(index);
        T changedValue = currentElement.value;
        currentElement.value = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T deletedValue;
        if (size == 1) {
            size = 0;
            return first.value;
        }
        if (index == size - 1) {
            deletedValue = last.value;
            last = last.prev;
            last.next = null;
            size--;
            return deletedValue;
        }
        if (index == 0) {
            deletedValue = first.value;
            first = first.next;
            first.prev = null;
            size--;
            return deletedValue;
        }
        Node<T> currentElement = findElement(index);
        unLink(currentElement);
        size--;
        return currentElement.value;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        Node<T> currentElement = first;
        while (currentElement != null && !(object == null && currentElement.value == null
                || object != null && object.equals(currentElement.value))) {
            currentElement = currentElement.next;
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

    private void checkIndex(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of List bounds with size " + size);
        }
    }

    private Node<T> findElement(int index) {
        Node<T> nextElement = first;
        int i = 0;
        while (i < index) {
            nextElement = nextElement.next;
            i++;
        }
        return nextElement;
    }

    private void unLink(Node<T> unlinkedNode) {
        unlinkedNode.prev.next = unlinkedNode.next;
        unlinkedNode.next.prev = unlinkedNode.prev;
    }
}
