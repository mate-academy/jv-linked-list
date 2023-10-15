package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INVALID_INDEX = "The provided index is invalid: ";
    private Node first;
    private Node last;
    private int size;

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0 || index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> preAddingNode = getNodeByIndex(index - 1);
            newNode.prev = preAddingNode;
            newNode.next = preAddingNode.next;
            preAddingNode.next = newNode;
            newNode.next.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeAtIndex = getNodeByIndex(index);
        T oldValue = nodeAtIndex.item;
        nodeAtIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndexForGetAndRemove(index);
        T removedElement;
        if (index == 0) {
            removedElement = (T) first.item;
            first = first.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
        } else {
            Node<T> nodeAtIndex = getNodeByIndex(index - 1);
            removedElement = nodeAtIndex.next.item;
            nodeAtIndex.next = nodeAtIndex.next.next;
            if (nodeAtIndex.next == null) {
                last = nodeAtIndex;
            } else {
                nodeAtIndex.next.prev = nodeAtIndex;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (Objects.equals(current.item, object)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    first = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    last = current.prev;
                }

                size--;
                return true; // Object successfully removed
            }
            current = current.next;
        }
        return false; // Object not found in the list
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void validateIndexForGetAndRemove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX + index);
        }
    }

    private void validateIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(INVALID_INDEX + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        validateIndexForGetAndRemove(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}