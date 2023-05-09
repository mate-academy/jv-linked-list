package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        Node<T> newLast = new Node<>(oldLast, value, null);
        last = newLast;
        if (oldLast == null) {
            first = newLast;
        } else {
            oldLast.next = newLast;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (var listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        final Node<T> node = getNodeByIndex(index);
        final T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> searchNode = first;
        while (searchNode != null) {
            if ((searchNode.value == object)
                    || (searchNode.value != null && searchNode.value.equals(object))) {
                unlink(searchNode);
                return true;
            }
            searchNode = searchNode.next;
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

    private T unlink(Node<T> node) {
        final T value = node.value;
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
        node.value = null;
        size--;
        return value;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index
                    + " The size is: " + size);
        }
    }

    private void linkBefore(T value, Node<T> node) {
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.prev == null) {
            first = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> searchedNode;
        if (index < (size >> 1)) {
            searchedNode = first;
            for (int i = 0; i < index; i++) {
                searchedNode = searchedNode.next;
            }
        } else {
            searchedNode = last;
            for (int i = size - 1; i > index; i--) {
                searchedNode = searchedNode.prev;
            }
        }
        return searchedNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
