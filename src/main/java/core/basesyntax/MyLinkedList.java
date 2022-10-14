package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int currentSize;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (currentSize == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        currentSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > currentSize) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            linkFirst(value);
        } else if (index == currentSize) {
            linkLast(value);
        } else {
            linkMiddle(value, index);
        }
        currentSize++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findByIndex(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(findByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (object == currentNode.item || object != null && object.equals(currentNode.item)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    private void indexValidation(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void linkFirst(T value) {
        if (currentSize == 0) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
        }
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
    }

    private void linkMiddle(T value, int index) {
        indexValidation(index);
        Node<T> currentNode = findByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
    }

    private T unlink(Node<T> node) {
        T value = node.item;
        if (node.prev == null && node.next != null) {
            node.next.prev = null;
            first = node.next;
        } else if (node.next == null && node.prev != null) {
            node.prev.next = null;
            last = node.prev;
        } else if (node.next == null && node.prev == null) {
            first = null;
            last = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        currentSize--;
        return value;
    }

    private Node<T> findByIndex(int index) {
        indexValidation(index);
        Node<T> currentNode;
        if (index <= (currentSize >> 1)) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = currentSize - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }
}
