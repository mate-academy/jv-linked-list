package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node;
        if (size == 0) {
            node = new Node<T>(null, value, null);
            tail = node;
            head = node;
        } else {
            node = new Node<T>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAddByIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head = new Node<>(null, value, getNodeByIndex(index));
            size++;
            return;
        }
        Node<T> newNode = new Node<>(getNodeByIndex(index).prev, value, getNodeByIndex(index));
        getNodeByIndex(index).prev.next = newNode;
        getNodeByIndex(index).prev = newNode;
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
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        T oldVal = newNode.value;
        newNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = head; i != null; i = i.next) {
                if (i.value == null) {
                    unlink(i);
                    return true;
                }
            }
        } else {
            for (Node<T> i = head; i != null; i = i.next) {
                if (object.equals(i.value)) {
                    unlink(i);
                    return true;
                }
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

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index
                    + ". Index should be inclusively between 0 and " + size);
        }
    }

    private void checkIndexAddByIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index
                    + ". Index should be negative and not greater than " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index < (size / 2)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> removedNode) {
        final T removedValue = removedNode.value;
        final Node<T> next = removedNode.next;
        final Node<T> prev = removedNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            removedNode.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            removedNode.next = null;
        }
        removedNode.value = null;
        size--;
        return removedValue;
    }
}


