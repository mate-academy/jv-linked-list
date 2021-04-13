package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index out of bounds";
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        linkAsLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        }
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T previousValue = node.value;
        node.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = first; i != null; i = i.next) {
            if (i.value == object || (object != null && object.equals(i.value))) {
                unlink(i);
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> node;
        if (index < (size / 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.previous;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            node.previous = null;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.previous = previousNode;
            node.next = null;
        }
        T removableNode = node.value;
        node.value = null;
        size--;
        return removableNode;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        }
    }

    private void linkAsLast(T value) {
        Node<T> previousLast = last;
        Node<T> newNode = new Node<>(previousLast, value, null);
        last = newNode;
        if (previousLast == null) {
            first = newNode;
        } else {
            previousLast.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> node) {
        Node<T> previousNode = node.previous;
        Node<T> newNode = new Node<>(previousNode, value, node);
        node.previous = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private static class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.previous = prev;
            this.value = value;
            this.next = next;
        }
    }
}
