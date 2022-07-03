package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
            return;
        }
        last.next = new Node<>(last, value, null);
        last = last.next;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newElement;
        if (index == 0) {
            newElement = new Node<>(null, value, first);
            first = newElement;
            newElement.next.prev = newElement;
            size++;
            return;
        }
        Node<T> element = first;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        newElement = new Node<>(element.prev, value, element);
        element.prev.next = newElement;
        element.prev = newElement;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        indexNotExist(index);
        Node<T> element = first;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        return element == null ? null : element.value;
    }

    @Override
    public T set(T value, int index) {
        indexNotExist(index);
        Node<T> element = first;
        Node<T> newElement;
        if (index == 0) {
            newElement = new Node<>(null, value, first.next);
            first = newElement;
            first.next.prev = newElement;
            return element.value;
        }
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        final T oldValue = element.value;
        newElement = new Node<>(element.prev, value, element.next);
        element.prev.next = newElement;
        element.next.prev = newElement;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexNotExist(index);
        if (size == 1) {
            size--;
            return first.value;
        } else if (index == 0) {
            final T removedValue = first.value;
            first = first.next;
            first.prev = null;
            size--;
            return removedValue;
        } else if (index + 1 == size) {
            final T removedValue = last.value;
            last = last.prev;
            last.prev.next = null;
            size--;
            return removedValue;
        }
        Node<T> element = first;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        element.prev.next = element.next;
        element.next.prev = element.prev;
        size--;
        return element.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> element = first;
        for (int i = 0; i < size; i++) {
            if (element.value == object
                    || (element.value != null && element.value.equals(object))) {
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

    private void indexNotExist(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<E> {
        private final E value;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
