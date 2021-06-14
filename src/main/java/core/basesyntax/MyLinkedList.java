package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node heap;
    private Node tall;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<T>(null, value, null);
            heap = newNode;
            tall = newNode;
        } else {
            Node<T> newNode = new Node<T>(tall, value, null);
            tall.next = newNode;
            tall = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index was failed ");
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, heap);
            heap.prev = newNode;
            heap = newNode;
            size++;
        } else {
            Node<T> newNode = new Node<T>(getNode(index - 1), value, getNode(index));
            getNode(index).prev = newNode;
            getNode(index - 1).next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> result = heap;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T result = getNode(index).item;
        getNode(index).item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size || size == 0) {
            throw new IndexOutOfBoundsException("Index was failed ");
        }
        T result = getNode(index).item;
        Node<T> indexNode = getNode(index);
        if (index == 0 && size > 1) {
            heap = indexNode.next;
            indexNode.next.prev = null;
            size--;
            return result;
        } else if (index == 0) {
            indexNode.item = null;
            size--;
            heap = null;
            tall = null;
            return result;
        } else if (index == size - 1) {
            tall = indexNode.prev;
            indexNode.prev = new Node<>(indexNode.prev.prev, indexNode.prev.item, null);
            size--;
            return result;
        }
        indexNode.prev.next = indexNode.next;
        indexNode.next.prev = indexNode.prev;
        size--;
        return indexNode.item;
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> result = heap;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result;
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
