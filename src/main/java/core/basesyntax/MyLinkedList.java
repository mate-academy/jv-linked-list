package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(lastNode, value, null);
        if (lastNode == null) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexValidation(index);
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        if (current.prev == null) {
            firstNode = newNode;
        } else {
            current.prev.next = newNode;
        }
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T elements : list) {
            add(elements);
        }
    }

    @Override
    public T get(int index) {
        indexValidation(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        while (current != null) {
            if (current.item == object
                    || current.item != null && current.item.equals(object)) {
                unlink(current);
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

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            firstNode = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            lastNode = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
    }

    private Node<T> getNode(int index) {
        indexValidation(index);
        Node<T> temporary;
        if (index < size / 2) {
            temporary = firstNode;
            for (int i = 0; i < index; i++) {
                temporary = temporary.next;
            }
        } else {
            temporary = lastNode;
            for (int i = size - 1; i > index; i--) {
                temporary = temporary.prev;
            }
        }
        return temporary;
    }
}
