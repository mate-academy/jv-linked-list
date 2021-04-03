package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public boolean add(T value) {
        addToTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        indexCheckForAdd(index);
        if (index == size) {
            addToTail(value);
        } else {
            addInBetween(value, getNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexCheckGeneral(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexCheckGeneral(index);
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheckGeneral(index);
        return deleteNode(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> value = head; value != null; value = value.next) {
            if (object == value.value || object != null && object.equals(value.value)) {
                deleteNode(value);
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

    private static class Node<T> {
        T value;
        Node<T> previous;
        Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;

        }
    }

    private void indexCheckGeneral(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    private void indexCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    private Node<T> getNode(int index) {
        indexCheckGeneral(index);
        if (index < (size >> 1)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
            return current;
        }
    }

    private void addToTail(T value) {
        final Node<T> current = tail;
        final Node<T> newNode = new Node<>(current, value, null);
        tail = newNode;
        if (current == null) {
            head = newNode;
        } else {
            current.next = newNode;
        }
        size++;
    }

    private void addInBetween(T value, Node<T> current) {
        final Node<T> nodeBefore = current.previous;
        final Node<T> newNode = new Node<>(nodeBefore, value, current);
        current.previous = newNode;
        if (nodeBefore == null) {
            head = newNode;
        } else {
            nodeBefore.next = newNode;
        }
        size++;
    }

    private T deleteNode(Node<T> current) {
        final T value = current.value;
        final Node<T> next = current.next;
        final Node<T> previous = current.previous;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            current.previous = null;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            current.next = null;
        }

        current.value = null;
        size--;
        return value;
    }
}
