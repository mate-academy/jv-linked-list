package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirst(value);
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
        if (isEmpty()) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);;
            head = head.prev;
        } else {
            Node<T> current = getNodeByIndex(index);
            Node<T> newNode = new Node<>(current.prev, value, current);
            current.prev.next = current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
        if (index == size - 1) {
            return tail.item;
        }
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
        Node<T> current = getNodeByIndex(index);
        if (current.prev == null) {
            head = current.next;
            current.next = null;
        } else if (current.next == null) {
            tail = current.prev;
            current.prev.next = null;
            current.prev = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        int nodeIndex = getIndexByValue(object);
        if (nodeIndex == -1) {
            return false;
        } else {
            remove(nodeIndex);
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size;
    }

    private void addFirst(T value) {
        head = tail = new Node<>(null, value, null);
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail = tail.next = newNode;
    }

    private int getIndexByValue(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if ((value == null && current.item == null)
                    || value != null && value.equals(current.item)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
