package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        final Node<T> leftNode = last;
        final Node<T> newNode = new Node<>(leftNode, value, null);
        last = newNode;
        if (leftNode == null) {
            first = newNode;
        } else {
            leftNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else {
            final Node<T> left = node(index).prev;
            final Node<T> newNode = new Node<>(left, value, node(index));
            node(index).prev = newNode;
            if (left == null) {
                first = newNode;
            } else {
                left.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elementList : list) {
            add(elementList);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> current = node(index);
        T oldVal = current.element;
        current.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = first; current != last.next; current = current.next) {
            if (current.element == object || object != null && object.equals(current.element)) {
                unlink(current);
                return true;
            }
        }
        return false;
    }

    T unlink(Node<T> current) {
        final T element = current.element;
        final Node<T> next = current.next;
        final Node<T> prev = current.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            current.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            current.next = null;
        }
        current.element = null;
        size--;
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    Node<T> node(int index) {
        checkElementIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = (size - 1); i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't element index: " + index);
        }
    }

    public void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't element index: " + index);
        }
    }
}
