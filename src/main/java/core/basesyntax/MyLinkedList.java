package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(null, value, null);
            first = node;
            last = node;
        } else {
            Node<T> node = new Node<>(last, value, null);
            last.next = node;
            node.prev = last;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> existingNode = getNode(index);
        Node<T> newNode = new Node<T>(existingNode.prev, value, existingNode);
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
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
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
        E value;
        Node prev;
        Node next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private T unlink(Node<T> node) {
        T value = node.value;
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev.value == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next.value == null) {
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
