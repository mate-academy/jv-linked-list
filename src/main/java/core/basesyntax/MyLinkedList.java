package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            addFront(value);
            return;
        }
        checkIndex(index);
        Node<T> temporaryNode = getNode(index - 1);
        Node<T> newNode = new Node<>(temporaryNode, value, temporaryNode.next);

        temporaryNode.next.prev = newNode;
        temporaryNode.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
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
        T deletedValue = element.value;

        element.value = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> element = getNode(index);
        unlink(element);
        return element.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> elementToRemove = getNode(object);
        if (elementToRemove != null) {
            unlink(elementToRemove);
            return true;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Unacceptable out of bound index" + index + "!"
                    + "Current linked list size: " + size);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> element;
        if (index < size / 2) {
            element = first;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
        } else {
            element = last;
            for (int i = size - 1; i > index; i--) {
                element = element.prev;
            }
        }
        return element;
    }

    private Node<T> getNode(T value) {
        Node<T> element;
        element = first;
        for (int i = 0; i < size; i++) {
            if (value == null ? element.value == null : value.equals(element.value)) {
                return element;
            }
            element = element.next;
        }
        return element;
    }

    private void addFront(T value) {
        Node<T> newNode = new Node<>(null, value, first);
        first = newNode;
        size++;
    }

    private void unlink(Node<T> element) {
        if (first.equals(element)) {
            size--;
            if (size == 0) {
                return;
            }
            first.next.prev = null;
            first = first.next;
            return;
        } else if (last.equals(element)) {
            last.prev.next = null;
            last = last.prev;
            size--;
            return;
        }

        element.prev.next = element.next;
        element.next.prev = element.prev;
        size--;
    }

    private static class Node<E> {
        private E value;
        private Node<E> prev;
        private Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
