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
        unlink(removedElement);
        return removedElement.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        for (int i = 0; i < size; i++) {
            if (object == current.value
                    || object != null && object.equals(current.value)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.previous;
        if (nextNode == null && previousNode == null) {
            firstNode = null;
            lastNode = null;
        } else if (previousNode == null) {
            firstNode = nextNode;
            nextNode.previous = null;
        } else if (nextNode == null) {
            lastNode = previousNode;
            previousNode.next = null;
        } else {
            previousNode.next = nextNode;
            nextNode.previous = previousNode;
        }
        size--;
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
        if (size / 2 >= index) {
            return getFromHead(index);
        } else {
            return getFromTail(index);
        }
    }

    private Node<T> getFromHead(int index) {
        Node<T> current = firstNode;
        int localSize = 0;
        while (localSize != index) {
            current = current.next;
            localSize++;
        }
        return current;
    }

    private Node<T> getFromTail(int index) {
        Node<T> current = lastNode;
        int localSize = (size - 1) - index;
        while (localSize > 0) {
            current = current.previous;
            localSize--;
        }
        return current;
    }
}
