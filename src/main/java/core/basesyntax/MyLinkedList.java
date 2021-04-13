package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    private static class Node<I> {
        private I element;
        private Node<I> previous;
        private Node<I> next;

        Node(Node<I> previous, I element, Node<I> next) {
            this.next = next;
            this.previous = previous;
            this.element = element;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> currentNode = new Node<>(null,value, head);
            head.previous = currentNode;
            head = currentNode;
            size++;
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.previous, value, currentNode);
        currentNode.previous.next = newNode;
        currentNode.previous = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T valueOfList : list) {
            add(valueOfList);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentValue = findNodeByIndex(index);
        T changedValue = currentValue.element;
        currentValue.element = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = findNodeByIndex(index);
        return unLink(removedNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (object == current.element || object != null && object.equals(current.element)) {
                unLink(current);
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = head;
            int i = 0;
            while (i < index) {
                currentNode = currentNode.next;
                i++;
            }
            return currentNode;
        }
        currentNode = tail;
        int i = size - 1;
        while (i > index) {
            currentNode = currentNode.previous;
            i--;
        }
        return currentNode;
    }

    private T unLink(Node<T> removedNode) {
        final T removedValue = removedNode.element;
        if (removedNode.next == null) {
            tail = removedNode.previous;
        } else {
            removedNode.next.previous = removedNode.previous;
        }
        if (removedNode.previous == null) {
            head = removedNode.next;
        } else {
            removedNode.previous.next = removedNode.next;
        }
        size--;
        return removedValue;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        }
    }
}
