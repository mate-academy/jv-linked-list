package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> target = getNodeByIndex(index);
            Node<T> prev = target.prev;
            Node<T> newNode = new Node<>(value, prev, target);
            target.prev = newNode;
            if (prev == null) {
                head = newNode;
            } else {
                prev.next = newNode;
            }
            size++;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Index '%d' out of bounds for size '%d'", index, size)
            );
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> target = getNodeByIndex(index);
        T oldValue = target.value;
        target.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNodeByIndex(index);
        unlink(target);
        return target.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> target = getNodeByValue(object);
        if (target == null) {
            return false;
        }
        unlink(target);
        return true;
    }

    private void unlink(Node<T> target) {
        Node<T> prev = target.prev;
        Node<T> next = target.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        size--;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> current = head;
        while (current != null) {
            if (value == current.value || value != null && value.equals(current.value)) {
                return current;
            }
            current = current.next;
        }
        return null;
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
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
