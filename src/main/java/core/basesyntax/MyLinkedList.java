package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            last = newNode;
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkElementIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (first == null || index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> current = getNodeByIndex(index - 1);
            newNode.next = current.next;
            current.next.prev = newNode;
            current.next = newNode;
            newNode.prev = current;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T newItem : list) {
            add(newItem);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return getNodeByIndex(index).newItem;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.newItem;
        node.newItem = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlinkNode(node);
        return node.newItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (Objects.equals(current.newItem, object)) {
                unlinkNode(current);
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
        return first == null;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < (size - 1)) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T removeFirst() {
        final T removedNewItem = first.newItem;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        size--;
        return removedNewItem;
    }

    private T removeLast() {
        final T removedNewItem = last.newItem;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        size--;
        return removedNewItem;
    }

    private void unlinkNode(Node<T> node) {
        if (node == first) {
            removeFirst();
        } else if (node == last) {
            removeLast();
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
    }

    private static class Node<T> {
        private T newItem;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T newItem, Node<T> next) {
            this.prev = prev;
            this.newItem = newItem;
            this.next = next;
        }
    }
}
