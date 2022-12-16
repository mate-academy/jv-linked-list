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
        checkIndexPossibility(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("The list is empty!");
        }
        for (var i : list) {
            this.add(i);
        }
    }

    @Override
    public T get(int index) {
        checkIndexExistence(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexExistence(index);
        final Node<T> node = getNodeByIndex(index);
        final T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexExistence(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if ((x.value == object) || (x.value != null && x.value.equals(object))) {
                unlink(x);
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

    private void checkIndexPossibility(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index
                    + " The size is: " + size);
        }
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

    private void checkIndexExistence(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index
                    + " The size is: " + size);
        }
    }

    private void linkBefore(T value, Node<T> node) {
        if (node == null) {
            throw new RuntimeException("There is no such element to link before!");
        }
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
        checkIndexExistence(index);
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
