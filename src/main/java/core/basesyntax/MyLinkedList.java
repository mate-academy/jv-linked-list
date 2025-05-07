package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (tail == null) {
            head = new Node<>(value, null, null);
            tail = head;
        } else {
            tail.next = new Node<>(value, tail, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> nodeNext = getNode(index);
        Node<T> nodePrevious = nodeNext.prev;
        Node<T> newNode = new Node<T>(value, nodePrevious, nodeNext);
        nodeNext.prev = newNode;
        if (nodePrevious != null) {
            nodePrevious.next = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> elementToChange = getNode(index);
        T oldItem = elementToChange.item;
        elementToChange.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNode(index);
        unlink(removedNode);
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == object || current.item != null
                    && current.item.equals(object)) {
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > (index + 1); i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " index out of bounds!");
        }
    }

    private T unlink(Node<T> removedNode) {
        if (removedNode.next == null) {
            if (removedNode.prev != null) {
                removedNode.prev.next = null;
            }
            tail = removedNode.prev;
        } else if (removedNode.prev == null) {
            removedNode.next.prev = null;
            head = removedNode.next;
        } else {
            removedNode.next.prev = removedNode.prev;
            removedNode.prev.next = removedNode.next;
        }
        size--;
        return removedNode.item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
