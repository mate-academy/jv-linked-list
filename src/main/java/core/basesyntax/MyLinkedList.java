package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> lastNode = tail;
        final Node<T> newNode = new Node<>(value, lastNode, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        checkRangeForIndex(index);
        Node<T> newNext = getElement(index);
        Node<T> newPred = newNext.prev;
        Node<T> newNode = new Node<>(value, newPred, newNext);
        newNext.prev = newNode;
        if (newPred == null) {
            head = newNode;
        } else {
            newPred.next = newNode;
        }
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkRangeForIndex(index);
        return getElement(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkRangeForIndex(index);
        Node<T> node = getElement(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkRangeForIndex(index);
        return unlink(getElement(index)).value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value == object || (object != null
                    && object.equals(node.value))) {
                unlink(node);
                return true;
            }
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

    private void checkRangeForIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index " + index
                    + " is not valid for size: " + size);
        }
    }

    private Node<T> getElement(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return node;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
