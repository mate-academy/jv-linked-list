package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        Node<T> newNode = new Node<>(null, value, head);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> currentNode = getNode(index);
            currentNode.prev.next = newNode;
            newNode.prev = currentNode.prev;
            newNode.next = currentNode;
            currentNode.prev = newNode;
        }
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        T result = getNode(index).item;
        getNode(index).item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkRemoveIndex(index);
        Node<T> indexNode = getNode(index);
        T result = indexNode.item;
        if (index == 0) {
            removeFirsIndex(index);
        } else if (index == size - 1) {
            removeLastIndex(index);
        } else {
            removeIndex(index);
        }
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == get(i) || (object != null && object.equals(get(i)))) {
                remove(i);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index was failed ");
        }
    }

    private void checkIndexToAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index was failed ");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> result;
        if (index < size / 2) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private void checkRemoveIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index was failed ");
        }
    }

    public void removeFirsIndex(int index) {
        Node<T> indexNode = getNode(index);
        if (size > 1) {
            head = indexNode.next;
            indexNode.next.prev = null;
            return;
        }
        indexNode.item = null;
        head = null;
        tail = null;
    }

    public void removeLastIndex(int index) {
        Node<T> indexNode = getNode(index);
        tail = indexNode.prev;
        indexNode.prev.next = null;
    }

    private void removeIndex(int index) {
        Node<T> indexNode = getNode(index);
        indexNode.prev.next = indexNode.next;
        indexNode.next.prev = indexNode.prev;
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
