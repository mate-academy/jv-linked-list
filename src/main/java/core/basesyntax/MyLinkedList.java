package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> last;
    private Node<T> first;
    private int size = 0;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> currentNode = findNode(index);
        T oldItem = currentNode.item;
        currentNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {

        Node<T> currentNode = first;
        while (currentNode != null) {
            if (object == null && currentNode.item == null) {
                unlink(currentNode);
                return true;
            } else if (object != null && object.equals(currentNode.item)) {
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

    private void linkLast(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value,null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is incorrect!");
        }
    }

    private void linkBefore(T value, Node<T> current) {
        final Node<T> nodeBefore = current.prev;
        final Node<T> newNode = new Node<>(nodeBefore, value, current);
        current.prev = newNode;
        if (nodeBefore == null) {
            first = newNode;
        } else {
            nodeBefore.next = newNode;
        }
        size++;
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode;
        if (index < (size / 2)) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> currentNode) {
        final T value = currentNode.item;
        final Node<T> next = currentNode.next;
        final Node<T> prev = currentNode.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            currentNode.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            currentNode.next = null;
        }

        currentNode.item = null;
        size--;
        return value;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is incorrect!");
        }
    }
}
