package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            setFirstValue(value);
            return;
        }
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        if (isEmpty()) {
            setFirstValue(value);
            return;
        }
        if (index == size) {
            linkLast(value);
            return;
        }
        linkBefore(value, findNodeByIndex(index));
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            this.add(item);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = findNodeByIndex(index);
        T oldItem = oldNode.item;
        oldNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNodeByIndex(index);
        T value = node.item;
        unlink(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        try {
            unlink(findNodeByValue(object));
        } catch (RuntimeException e) {
            return false;
        }
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

    private void checkAddIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Your index was " + index
                    + ", but size is " + size);
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Your index was " + index
                    + ", but size is " + size);
        }
    }

    private Node<T> findNodeByValue(T value) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (value == node.item || value != null
                    && value.equals(node.item)) {
                return node;
            }
            node = node.next;
        }
        throw new RuntimeException("Element " + value + " didn't find");
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        int part = size % 2 == 0 ? size / 2 : size / 2 + 1;
        if (index < part) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
        Node<T> node = last;
        for (int i = 0; i < size - index - 1; i++) {
            node = node.prev;
        }
        return node;
    }

    private void linkBefore(T element, Node<T> next) {
        Node<T> prev = next.prev;
        Node<T> newNode = new Node<>(prev, element, next);
        next.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private void setFirstValue(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        first = newNode;
        last = newNode;
        size++;
    }

    private void linkLast(T element) {
        Node<T> newNode = new Node<>(last, element, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private void unlink(Node<T> node) {
        size--;
        if (node == first && node == last) {
            first = null;
            last = null;
            return;
        } else if (node == first) {
            first = node.next;
            node.next.prev = null;
            return;
        } else if (node == last) {
            last = node.prev;
            node.prev.next = null;
            return;
        }
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
