package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            lastNode = new Node<>(null, value, null);
            firstNode = lastNode;
        } else {
            Node<T> newElement = new Node<>(lastNode, value, null);
            lastNode.next = newElement;
            lastNode = newElement;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkBounds(index);
        if (index == 0) {
            Node<T> current = new Node<>(null, value, firstNode);
            firstNode.previous = current;
            firstNode = current;
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(current.previous, value, current);
            current.previous.next = newNode;
            current.previous = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkBounds(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkBounds(index);
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkBounds(index);
        Node<T> removedElement = getNode(index);
        final T result = removedElement.value;
        if (removedElement.next == null) {
            lastNode = removedElement.previous;
        } else {
            removedElement.next.previous = removedElement.previous;
        }
        if (removedElement.previous == null) {
            firstNode = removedElement.next;
        } else {
            removedElement.previous.next = removedElement.next;
        }
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        for (int i = 0; i < size; i++) {
            if (object == current.value
                    || object != null && object.equals(current.value)) {
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

    private class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        private Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    private void checkBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bound!");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        int counter = 0;
        if (index <= size / 2) {
            current = firstNode;
            while (counter < index) {
                current = current.next;
                counter++;
            }
        } else {
            current = lastNode;
            counter = size - 1;
            while (counter > index) {
                current = current.previous;
                counter--;
            }
        }
        return current;
    }
}
