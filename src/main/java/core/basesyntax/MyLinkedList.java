
package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

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
        checkPositionIndex(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> nextNode = getNode(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);

        nextNode.prev = newNode;

        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Objects.requireNonNull(list);

        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);

        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            if (Objects.equals(current.value, object)) {
                unlink(current);
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

    private Node<T> getNode(int index) {
        if (index < size / 2) {
            Node<T> current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

            return current;
        }

        Node<T> current = tail;

        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }

        return current;
    }

    private T unlink(Node<T> node) {
        T value = node.value;
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }

        node.value = null;
        node.prev = null;
        node.next = null;

        size--;
        return value;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }
    }
}
