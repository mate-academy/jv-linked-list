package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> element = new Node<>(null, value, null);
        if (head == null) {
            head = tail = element;
        } else {
            tail.next = element;
            element.prev = tail;
            tail = element;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAddition(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = getNode(index);
        Node<T> newElement = new Node<>(current.prev, value, current);
        updatePreviousLink(current, newElement);
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
        Node<T> current = getNode(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        unlinkNode(current);
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedElement = head;
        for (int i = 0; i < size; i++) {
            if (isEqual(removedElement.value,object)) {
                remove(i);
                return true;
            }
            removedElement = removedElement.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    private boolean isEqual(T firstValue, T secondValue) {
        return firstValue == secondValue || firstValue != null && firstValue.equals(secondValue);
    }

    private void updatePreviousLink(Node<T> current, Node<T> element) {
        if (current.prev != null) {
            current.prev.next = element;
        } else {
            head = element;
        }
        current.prev = element;
    }

    private void unlinkNode(Node<T> current) {
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " is out of bounds. "
                    + size);
        }
    }

    private void checkIndexForAddition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " is out of bounds. "
                    + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
