package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == size) {
            linkLast(value);
        } else if (index == 0) {
            linkFirst(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            linkLast(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);

        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;

        while (currentNode != null) {
            if (Objects.equals(object, currentNode.item)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void linkFirst(T value) {
        Node<T> oldFirst = first;
        Node<T> newNode = new Node<>(null, value, oldFirst);

        first = newNode;

        if (oldFirst == null) {
            last = newNode;
        } else {
            oldFirst.prev = newNode;
        }

        size++;
    }

    void linkLast(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value, null);

        last = newNode;

        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }

        size++;
    }

    private void linkBefore(T value, Node<T> successor) {
        Node<T> predecessor = successor.prev;
        Node<T> newNode = new Node<>(predecessor, value, successor);

        successor.prev = newNode;

        if (predecessor == null) {
            first = newNode;
        } else {
            predecessor.next = newNode;
        }

        size++;
    }

    private T unlink(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;

        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.prev = previousNode;
            node.next = null;
        }
        T removedValue = node.item;
        node.item = null;
        size--;

        return removedValue;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> currentNode = first;

            for (int currentIndex = 0; currentIndex < index; currentIndex++) {
                currentNode = currentNode.next;
            }

            return currentNode;
        }

        Node<T> currentNode = last;

        for (int currentIndex = size - 1; currentIndex > index; currentIndex--) {
            currentNode = currentNode.prev;
        }

        return currentNode;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size
            );
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size
            );
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
