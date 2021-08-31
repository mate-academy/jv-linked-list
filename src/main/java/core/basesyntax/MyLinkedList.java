package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        last = new Node<>(null, null, null);
        last.next = last.previous = last;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        Node<T> tail = index == size ? last : getNode(index);
        Node<T> element = new Node<>(value, tail, tail.previous);
        element.previous.next = element.next.previous = element;
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
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
        Node<T> element = last.next;
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
        Objects.checkIndex(index, size);
        Node<T> element = last;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++) {
                element = element.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                element = element.previous;
            }
        }
        return element;
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
        private T value;
        private Node<T> next;
        private Node<T> previous;

        private Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
