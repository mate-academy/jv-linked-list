package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> newNode = new Node<>(value, tail, null);
        if (tail != null) {
            tail.next = newNode;
        }
        checkHeadTail(newNode);
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> replacedNode;
        Node<T> newNode;
        if (index == size) {
            newNode = new Node<>(value, tail, null);
        } else {
            replacedNode = getAt(index);
            if (replacedNode != null) {
                newNode = new Node<>(value, replacedNode.previous, replacedNode);
                replacedNode.previous = newNode;
            } else {
                newNode = new Node<>(value, null, null);
            }
        }
        if (newNode.previous != null) {
            newNode.previous.next = newNode;
        }
        checkHeadTail(newNode);
        size++;
    }

    @Override
    public void addAll(List<T> values) {
        for (T toAdd : values) {
            add(toAdd);
        }
    }

    @Override
    public T get(int index) {
        return getAt(index).value;
    }

    @Override
    public T set(T value, int index) {
        final Node<T> foundNode = getAt(index);
        final T oldValue = foundNode.value;
        foundNode.value = value;
        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T remove(int index) {
        Node<T> foundNode = getAt(index);
        unlink(foundNode);
        return foundNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            T value = current.value;
            if (value == null && object == null
                    || value != null && value.equals(object)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void unlink(Node<T> foundNode) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (foundNode.previous == null) {
            head = foundNode.next;
            head.previous = null;
        } else if (foundNode.next == null) {
            tail = foundNode.previous;
            tail.next = null;
        } else {
            foundNode.previous.next = foundNode.next;
            foundNode.next.previous = foundNode.previous;
        }
        size--;
    }

    private Node<T> getAt(int index) {
        checkBounds(index);
        Node<T> result;
        if (index < (size - 1) / 2) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = tail;
            for (int i = 0; i < size - (index + 1); i++) {
                result = result.previous;
            }
        }
        return result;
    }

    private void checkBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of range 0 - %d", index, size)
            );
        }
    }

    private void checkHeadTail(Node<T> node) {
        if (node.next == null) {
            tail = node;
        }
        if (node.previous == null) {
            head = node;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }
}
