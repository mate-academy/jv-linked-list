package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        last = new Node<>(value, null, last);
        if (first == null) {
            first = last;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = getNode(index);
        new Node<>(value, current, current.previous);
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return removeNode(getNode(index));
    }

    @Override
    public boolean remove(T t) {
        Node<T> current = first;
        while (current != null) {
            if (current.value == t || current.value != null && current.value.equals(t)) {
                removeNode(current);
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

    private void checkIndexExists(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private T removeNode(Node<T> node) {
        if (node.equals(first)) {
            first = node.next;
        }
        if (node.equals(last)) {
            last = node.previous;
        }
        node.delete();
        size--;
        return node.value;
    }

    public Node<T> getNode(int index) {
        checkIndexExists(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        private Node(T current, Node<T> nextNode, Node<T> previousNode) {
            value = current;
            next = nextNode;
            previous = previousNode;
            if (next != null) {
                next.previous = this;
            }
            if (previous != null) {
                previous.next = this;
            }
        }

        private void delete() {
            if (next != null) {
                next.previous = previous;
            }
            if (previous != null) {
                previous.next = next;
            }
        }
    }
}
