package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexRangeInAdd(index);
        if (size == index) {
            add(value);
            return;
        } else {
            Node<T> node = findNodeByIndex(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            if (node.prev == null) {
                head = newNode;
            } else {
                node.prev.next = newNode;
            }
            node.prev = newNode;
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
        checkIndexRange(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexRange(index);
        Node<T> foundNodeByIndex = findNodeByIndex(index);
        T oldElement = foundNodeByIndex.element;
        foundNodeByIndex.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndexRange(index);
        Node<T> removedNode = findNodeByIndex(index);
        unlink(removedNode);
        size--;
        return removedNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNodeByValue = findNodeByValue(object);
        if (removedNodeByValue == null) {
            return false;
        } else if (size == 0) {
            head = null;
        } else if (size == 1) {
            tail = null;
        }
        unlink(removedNodeByValue);
        size--;
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

    private void checkIndexRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " out of bounds "
                    + "for size "
                    + size);
        }
    }

    private void checkIndexRangeInAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " out of bounds "
                    + "for size "
                    + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> findNodeByValue(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((currentNode.element == object) ||
                    currentNode.element != null &&
                            currentNode.element.equals(object)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = head.next;
        } else if (node.next == null) {
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
