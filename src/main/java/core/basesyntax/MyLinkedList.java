package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> nodeToAdd = new Node<>(tail, value, null);
            tail.next = nodeToAdd;
            tail = nodeToAdd;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIfIndexOutOfBonds(index, size);
        if ((index == 0 && head == null) || index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head = new Node<>(null, value, head);
        } else {
            Node<T> previous = getNodeByIndex(index - 1);
            Node<T> nodeToAdd = new Node<>(previous, value, previous.next);
            previous.next.prev = nodeToAdd;
            previous.next = nodeToAdd;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIfIndexOutOfBonds(index, size - 1);
        Node<T> current = getNodeByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexOutOfBonds(index, size - 1);
        Node<T> current = getNodeByIndex(index);
        T valueBeforeSet = current.value;
        current.value = value;
        return valueBeforeSet;
    }

    @Override
    public T remove(int index) {
        checkIfIndexOutOfBonds(index, size - 1);
        T removedValue;
        if (index == 0) {
            removedValue = head.value;
            unlink(head);
        } else {
            Node<T> previous = getNodeByIndex(index - 1);
            removedValue = previous.next.value;
            unlink(previous.next);
        }
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int index = 0; index < size; index++) {
            if ((current.value != null && current.value.equals(object))
                    || current.value == object) {
                remove(index);
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

    private void checkIfIndexOutOfBonds(int index, int upperBound) {
        if (index < 0 || (index > upperBound)) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of bounds " + 0 + " " + "and " + upperBound);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = index < size / 2 ? head : tail;
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

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            Node<T> previous = node.prev;
            previous.next = node.next;
            node.next.prev = previous;
        }
        size--;
    }
}
