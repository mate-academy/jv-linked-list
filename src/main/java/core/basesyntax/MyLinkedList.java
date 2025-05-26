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
        checkPositionIndex(index);

        if (index == size) {
            addLast(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            addLast(item);
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
        Node<T> oldNode = findNodeByIndex(index);
        T oldItem = oldNode.item;
        oldNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> node = findNodeByIndex(index);
        T value = node.item;
        unlink(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    unlink(node);
                    return true;
                }
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

    private void addLast(T element) {
        final Node<T> secondLast = last;
        final Node<T> newNode = new Node<>(secondLast, element, null);
        last = newNode;
        if (secondLast == null) {
            first = newNode;
        } else {
            secondLast.next = newNode;
        }
        size++;
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is not correct, "
                    + "it should be within the Size: " + size);
        }
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: " + index + " is not correct, "
                    + "it should be within the Size: " + size);
        }
    }

    private void linkBefore(T element, Node<T> successor) {
        final Node<T> newPrev = successor.prev;
        final Node<T> newNode = new Node<>(newPrev, element, successor);
        successor.prev = newNode;
        if (newPrev == null) {
            first = newNode;
        } else {
            newPrev.next = newNode;
        }
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size / 2)) {
            Node<T> firstNode = first;
            for (int i = 0; i < index; i++) {
                firstNode = firstNode.next;
            }
            return firstNode;
        } else {
            Node<T> lastNode = last;
            for (int i = size - 1; i > index; i--) {
                lastNode = lastNode.prev;
            }
            return lastNode;
        }
    }

    private void unlink(Node<T> node) {
        final T element = node.item;
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
}
