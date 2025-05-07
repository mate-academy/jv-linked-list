package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        this.size = 0;
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (Object element : list) {
            add((T) element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> element = node(index);
        T oldVal = element.value;
        element.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = first; i != null; i = i.next) {
            if (i.value == object || i.value != null && i.value.equals(object)) {
                unlink(i);
                return true;
            }
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

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> first = this.first;
            for (int i = 0; i < index; i++) {
                first = first.next;
            }
            return first;
        } else {
            Node<T> last = this.last;
            for (int i = size - 1; i > index; i--) {
                last = last.prev;
            }
            return last;
        }
    }

    private void linkLast(T value) {
        final Node<T> last = this.last;
        final Node<T> newNode = new Node<>(last, value, null);
        this.last = newNode;
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> node) {
        final Node<T> previous = node.prev;
        final Node<T> newNode = new Node<>(previous, value, node);
        node.prev = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> removedElement) {
        final T element = removedElement.value;
        final Node<T> next = removedElement.next;
        final Node<T> prev = removedElement.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return element;
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
