package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkAddingIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkNode(value, getNode(index));
        }
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
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        int index = indexOf(object);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node<E> {
        private E value;
        private Node prev;
        private Node next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkLast(T value) {
        final Node<T> oldLast = last;
        final Node<T> newNode = new Node<T>(last, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    private void linkNode(T value, Node<T> existingNode) {
        Node<T> previousNode = existingNode.prev;
        Node<T> newNode = new Node<T>(previousNode, value, existingNode);
        existingNode.prev = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        T value = node.value;
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        return value;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d is out of list bounds.", index));
        }
    }

    private void checkAddingIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d is out of list bounds.", index));
        }
    }

    private Node<T> getNode(int index) {
        if (index < (size >> 1)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private int indexOf(T object) {
        int index = 0;
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
