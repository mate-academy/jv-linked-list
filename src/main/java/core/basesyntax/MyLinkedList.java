package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String OUT_OF_BOUNDS_EXCEPTION = "Index out of bounds!";
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> nodeToAdd = new Node<>(last, value, null);
        if (size == 0) {
            first = nodeToAdd;
        } else {
            last.next = nodeToAdd;
        }
        last = nodeToAdd;
        size++;
    }

    @Override
    public void add(T value, int index) {
        rangeCheckForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeToShift = getNode(index);
        Node<T> nodeToAdd = new Node<>(nodeToShift.prev, value, nodeToShift);
        if (index == 0) {
            first = nodeToAdd;
        } else {
            nodeToShift.prev.next = nodeToAdd;
        }
        nodeToShift.prev = nodeToAdd;
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
        T oldValue = getNode(index).item;
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.item == object || object != null && object.equals(currentNode.item)) {
                unlink(getNode(index));
                return true;
            }
            currentNode = currentNode.next;
            index++;
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
            throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_EXCEPTION);
        }
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_EXCEPTION);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size >> 1) {
            currentNode = first;
            for (int i = 0; i < index; ++i) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; --i) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
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
        T element = node.item;
        node.item = null;
        --size;
        return element;
    }
}
