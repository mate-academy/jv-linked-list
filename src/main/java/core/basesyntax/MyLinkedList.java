package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> element = new Node<>(last, value, null);
        if (size != 0) {
            last.next = element;
        } else {
            first = element;
        }
        last = element;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> node = new Node<>(null, value, first);
            first.previous = node;
            first = node;
        } else {
            Node<T> current = getNode(index);
            Node<T> before = current.previous;
            Node<T> node = new Node<>(before, value, current);
            current.previous = node;
            before.next = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element, size);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> element = getNode(index);
        T prevValue = element.value;
        element.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> element = getNode(index);
        unlink(element);
        return element.value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> element = first;
        for (int i = 0; i < size; i++) {
            if (t == element.value || t != null && t.equals(element.value)) {
                remove(i);
                return true;
            }
            element = element.next;
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
        if (index < (size >> 1)) {
            Node<T> element = first;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
            return element;
        } else {
            Node<T> element = last;
            for (int i = size - 1; i > index; i--) {
                element = element.previous;
            }
            return element;
        }
    }

    private void unlink(Node<T> node) {
        if (node.previous == null) {
            first = node.next;
        } else {
            node.previous.next = node.next;
        }
        if (node.next == null) {
            last = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index...");
        }
    }

    private static class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        private Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
