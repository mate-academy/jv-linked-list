package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            last = new Node<>(null, value, null);
            first = last;
        } else {
            Node<T> newNode = new Node<>(first, value, null);
            first.next = newNode;
            first = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, last);
            last = newNode;
            last.next.prev = newNode;
        } else {
            Node<T> nodeOfIndex = getNode(index);
            Node<T> newNode = new Node<>(nodeOfIndex.prev, value, nodeOfIndex);
            nodeOfIndex.prev.next = newNode;
            nodeOfIndex.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkingIndex(index);
        T old = getNode(index).value;
        getNode(index).value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkingIndex(index);
        T removedElement = getNode(index).value;
        unlink(getNode(index));
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        try {
            remove(getIndex(object));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
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
        checkingIndex(index);
        if (index <= size / 2) {
            Node<T> node = last;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else if (index > size / 2) {
            Node<T> node = first;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
        return null;
    }

    private int getIndex(T object) throws NoSuchElementException {
        Node<T> node = last;
        for (int i = 0; i < size; i++) {
            if (node.value == object || node.value.equals(object)) {
                return i;
            }
            node = node.next;
        }
        throw new NoSuchElementException();
    }

    private void checkingIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void unlink(Node<T> node) {
        if (node == last && size == 1) {
            last = null;
            first = null;
        } else if (node == last && size > 1) {
            last = last.next;
            last.prev = null;
        } else if (node == first) {
            node.prev.next = null;
            first = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}
