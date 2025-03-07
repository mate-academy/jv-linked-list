package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        addToTheTop(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addToTheTop(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            if (first != null) {
                first.prev = newNode;
            }
            first = newNode;
            size++;
        } else {
            checkIndexBounds(index);
            Node<T> currentNode = node(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (currentNode.prev != null) {
                currentNode.prev.next = newNode;
            }
            currentNode.prev = newNode;

            if (newNode.next == null) {
                last = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            addToTheTop(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexBounds(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexBounds(index);
        Node<T> newNode = node(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexBounds(index);
        Node<T> node = node(index);
        T element = node.item;
        unlink(node);
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = first;
        for (int i = 0; i < size; i++) {
            if ((newNode.item != null && newNode.item.equals(object))
                    || (newNode.item == null && object == null)) {
                unlink(newNode);
                return true;
            }
            newNode = newNode.next;
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

    private void checkIndexBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", Size: " + size + ". Out of bound.");
        }
    }

    private void addToTheTop(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    Node<T> node(int index) {
        if (index == 0) {
            return first;
        }
        if (index == size) {
            return last;
        }
        if (index > size / 2) {
            Node<T> newNode = last;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
            return newNode;
        }
        Node<T> newNode = first;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        return newNode;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
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
        node.item = null;
        size--;
    }

    static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
