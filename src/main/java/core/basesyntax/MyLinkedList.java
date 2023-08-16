package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNode(index));
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> targetNode = getNode(index);
        T oldVal = targetNode.item;
        targetNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = getNodeByObject(object);
        if (nodeToRemove != null) {
            unlink(nodeToRemove);
            return true;
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

    private Node<T> getNode(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        if (index < (size >> 1)) {
            Node<T> currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void linkBefore(T value, Node<T> nextNode) {
        final Node<T> pred = nextNode.prev;
        final Node<T> newNode = new Node<>(pred, value, nextNode);
        nextNode.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private void linkFirst(T value) {
        final Node<T> f = first;
        final Node<T> newNode = new Node<>(null, value, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    void linkLast(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> nodeToRemove) {
        final T element = nodeToRemove.item;
        final Node<T> next = nodeToRemove.next;
        final Node<T> prev = nodeToRemove.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            nodeToRemove.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            nodeToRemove.next = null;
        }

        nodeToRemove.item = null;
        size--;
        return element;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private Node<T> getNodeByObject(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                return node;
            }
            node = node.next;
        }
        return null;
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
