package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index, true);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> nextNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(value, nextNode.prev, nextNode);

        if (nextNode.prev != null) {
            nextNode.prev.next = newNode;
        } else {
            head = newNode;
        }

        nextNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index, false);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, false);
        Node<T> current = findNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, false);
        Node<T> nodeToRemove = findNodeByIndex(index);
        T oldValue = nodeToRemove.value;
        unlink(nodeToRemove);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.value == null) || (object != null && object.equals(current.value))) {
                unlink(current);
                size--;
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

    private T unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        T removedValue = node.value;
        node.value = null;
        node.prev = null;
        node.next = null;
        return removedValue;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
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

    private void validateIndex(int index, boolean isAddOperation) {
        if (isAddOperation) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
            }
        } else {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
            }
            if (size == 0) {
                throw new NoSuchElementException("Cannot perform operation on an empty list.");
            }
        }
    }
}
