package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node(null, value, null);
        if (isEmpty()) {
            first = last = newNode;
            size++;
            return true;
        }
        last.next = newNode;
        newNode.prev = last;
        last = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> oldNode = getNode(index);
        Node<T> newNode = new Node<T>(oldNode.prev, value, oldNode);
        if (index == 0) {
            first = newNode;
        }
        if (oldNode.prev != null) {
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T elem : list) {
            add(elem);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> result;
        if (index < size / 2) {
            result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return (T) result.element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T removedValue = node.element;
        node.element = value;
        return removedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        node.prev = node.next = null;
        size--;
        return node.element;
    }

    @Override
    public boolean remove(T t) {
        int index = 0;
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.element == t || (node.element != null && node.element.equals(t))) {
                remove(index);
                return true;
            }
            index++;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> result;
        if (index < size / 2) {
            result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
