package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ZERO = 0;
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
            node.prev = last;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " absent in this list");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = new Node<>(value);
        if (index == 0) {
            if (first == null) {
                node.next = null;
                node.prev = null;
                first = node;
                last = node;
            } else {
                first.prev = node;
                node.next = first;
                first = node;
                node.prev = null;
            }
            size++;
            return;
        }
        Node<T> tempNode = findNodeByIndex(index);
        Node<T> previous = tempNode.prev;
        previous.next = node;
        tempNode.prev = node;
        node.prev = previous;
        node.next = tempNode;
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
        checkIndex(index);
        Node<T> tempNode = findNodeByIndex(index);
        return tempNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> tempNode = findNodeByIndex(index);
        T returnedValue = tempNode.item;
        tempNode.item = value;
        return returnedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> tempNode = findNodeByIndex(index);
        return unlink(index, tempNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempNode = first;
        for (int i = 0; i < size; i++) {
            if (tempNode.item == object || (object != null && object.equals(tempNode.item))) {
                unlink(i, tempNode);
                return true;
            }
            tempNode = tempNode.next;
        }
        return false;
    }

    private T unlink(int index, Node<T> tempNode) {
        T removedItem = tempNode.item;
        if (index == 0) {
            if (first.next == null) {
                first = null;
                size = 0;
                return removedItem;
            }
            first.next.prev = null;
            first = first.next;
            size--;
            return removedItem;
        }
        if (index == size - 1) {
            last.prev.next = null;
            last = last.prev;
            size--;
            return removedItem;
        }
        tempNode.prev.next = tempNode.next;
        tempNode.next.prev = tempNode.prev;
        size--;
        return removedItem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == ZERO;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < size / 2) {
            return findFromHead(index);
        } else {
            return findFromTale(index);
        }
    }

    private Node<T> findFromHead(int index) {
        Node<T> tempNode = first;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        return tempNode;
    }

    private Node<T> findFromTale(int index) {
        Node<T> tempNode = last;
        for (int i = size - 1; i > index; i--) {
            tempNode = tempNode.prev;
        }
        return tempNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " absent in this list");
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(E element) {
            this.item = element;
        }
    }
}
