package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int NOT_FOUND = -1;
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {

    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of size " + size);
        }

        if (index == 0) {
            linkFirst(value);
        } else if (index == size) {
            linkLast(value);
        } else {
            link(value, getNode(index));
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
        isIndexValid(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        isIndexValid(index);
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexValid(index);

        T oldValue;
        if (index == 0) {
            oldValue = first.item;
            unlinkFirst();
        } else if (index == size - 1) {
            oldValue = last.item;
            unlinkLast();
        } else {
            Node<T> node = getNode(index);
            oldValue = node.item;
            unlink(node);
        }

        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexOfNodeByValue(object);
        if (index == NOT_FOUND) {
            return false;
        }

        remove(index);
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

    private void isIndexValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of size " + size);
        }
    }

    private int getIndexOfNodeByValue(T value) {
        Node<T> result = first;

        int i = 0;
        while (i < size) {
            if (result.item == value || (value != null && value.equals(result.item))) {
                return i;
            }
            result = result.next;
            i++;
        }

        return NOT_FOUND;
    }

    private Node<T> getNode(int index) {
        Node<T> result;
        if (size / 2 > index) {
            result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private void linkLast(T value) {
        Node<T> currentLast = last;
        Node<T> newNode = new Node<>(last, value, null);
        last = newNode;

        if (currentLast == null) {
            first = newNode;
        } else {
            currentLast.next = newNode;
        }

        size++;
    }

    private void linkFirst(T value) {
        Node<T> currentFirst = first;
        Node<T> newNode = new Node<>(null, value, first);

        if (first == null) {
            last = newNode;
        } else {
            currentFirst.prev = newNode;
        }

        first = newNode;
        size++;
    }

    private void link(T value, Node<T> currentNode) {
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    private void unlink(Node<T> removedNode) {
        removedNode.prev.next = removedNode.next;
        removedNode.next.prev = removedNode.prev;
        size--;
    }

    private void unlinkFirst() {
        Node<T> next = first.next;

        if (next == null) {
            first = null;
            last = null;
        } else {
            first = next;
            first.prev = null;
        }

        size--;
    }

    private void unlinkLast() {
        Node<T> prev = last.prev;

        if (prev == null) {
            first = null;
            last = null;
        } else {
            last = prev;
            last.next = null;
        }

        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
