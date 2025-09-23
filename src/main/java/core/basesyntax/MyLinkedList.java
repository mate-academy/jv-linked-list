package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            firstInput(value);
            return;
        }

        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be between 0 and size inclusive");
        }

        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            addMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexIsOutOfBound(index);

        if (index == size) {
            add(value);
            return value;
        }

        Node<T> currentNode = getNodeByIndex(index);
        final T oldValue = currentNode.item;
        currentNode.item = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexIsOutOfBound(index);

        Node<T> removedNode = getNodeByIndex(index);
        unlinkNode(removedNode);
        size--;

        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNodeByValue(object);

        if (currentNode == null) {
            return false;
        }

        unlinkNode(currentNode);
        size--;

        return true;
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
        checkIfIndexIsOutOfBound(index);

        Node<T> currentNode = first;

        for (int i = 0; i < size; i++) {
            if (i == index) {
                return currentNode;
            }

            currentNode = currentNode.next;
        }

        return null;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> currentNode = first;

        while (currentNode != null) {
            if (currentNode.item == value
                    || currentNode.item != null && currentNode.item.equals(value)) {
                return currentNode;
            }

            currentNode = currentNode.next;
        }

        return null;
    }

    private void checkIfIndexIsOutOfBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be non-negative and less than size");
        }
    }

    private void firstInput(T value) {
        Node<T> firstAndLastNode = new Node<>(null, value, null);
        first = firstAndLastNode;
        last = firstAndLastNode;
        size++;
    }

    private void unlinkNode(Node<T> removedNode) {
        if (removedNode.prev == null) {
            first = removedNode.next;
            return;
        }

        if (removedNode.next == null) {
            last = removedNode.prev;
            return;
        }

        removedNode.prev.next = removedNode.next;
        removedNode.next.prev = removedNode.prev;
    }

    private void addFirst(T value) {
        if (isEmpty()) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
        }
    }

    private void addLast(T value) {
        Node<T> newLastNode = new Node<>(last, value, null);
        last.next = newLastNode;
        last = newLastNode;
        size++;
    }

    private void addMiddle(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    public static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
