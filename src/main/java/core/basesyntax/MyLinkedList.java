package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementByIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementByIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementByIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.element == object || node.element != null && node.element.equals(object)) {
                unlink(node);
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

    private void checkElementByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void linkBefore(T value, Node<T> before) {
        final Node<T> pre = before.prev;
        final Node<T> newNode = new Node<>(pre, value, before);
        before.prev = newNode;
        if (pre == null) {
            head = newNode;
        } else {
            pre.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> value) {
        final T element = value.element;
        final Node<T> next = value.next;
        final Node<T> prev = value.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return element;
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        if (index < size >> 1) {
            node = head;
            while (index > 0) {
                node = node.next;
                index--;
            }
        } else {
            node = tail;
            while (index < size - 1) {
                node = node.prev;
                index++;
            }
        }
        return node;
    }

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
