package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE_OUT_OF_BOUNDS
            = "The index passed to any of the methods is invalid";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> current = findCurrent(index);
            newNode.next = current;
            newNode.prev = current.prev;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    public Node<T> findCurrent(int index) {
        validateIndex(index, size);
        Node<T> current = (index < size / 2) ? head : tail;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public T get(int index) {
        validateIndex(index, size);
        return findCurrent(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, size);
        Node<T> current = findCurrent(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, size);
        Node<T> currentNode = findCurrent(index);
        T removedValue = currentNode.value;
        unlink(currentNode);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (node == tail) {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void validateIndex(int index, int upperBound) {
        if (index < 0 || index >= upperBound) {
            throw new IndexOutOfBoundsException(MESSAGE_OUT_OF_BOUNDS + ": " + index);
        }
    }
}
