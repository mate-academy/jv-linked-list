package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLast(value);
        } else {
            addInside(value, findNode(index));
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
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> targetedNode = findNode(index);
        T replacedItem = targetedNode.item;
        targetedNode.item = value;
        return replacedItem;
    }

    @Override
    public T remove(int index) {
        T removedItem = findNode(index).item;
        unlink(findNode(index));
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if (object != null && object.equals(x.item) || object == x.item) {
                unlink(x);
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

    private void addLast(T value) {
        final Node<T> lastElement = last;
        final Node<T> newNode = new Node<>(lastElement, value, null);
        last = newNode;
        if (lastElement == null) {
            first = newNode;
        } else {
            lastElement.next = newNode;
        }
        size++;
    }

    private void chekIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Element with index " + index + " not exist");
        }
    }

    private Node<T> findNode(int index) {
        chekIndex(index);
        boolean choosePartOfList = index >= (size / 2);
        Node<T> node = choosePartOfList ? last : first;
        int middleIndex = choosePartOfList ? size - index - 1 : index;
        for (int i = 0; i < middleIndex; i++) {
            node = choosePartOfList ? node.prev : node.next;
        }
        return node;
    }

    private void addInside(T e, Node<T> node) {
        final Node<T> prev = node.prev;
        final Node<T> newNode = new Node<>(prev, e, node);
        node.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private void unlink(Node<T> node) {
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
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
        node.item = null;
        size--;
    }

    private static class Node<E> {
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
