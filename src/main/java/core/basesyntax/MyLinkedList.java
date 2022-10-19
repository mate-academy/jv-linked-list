package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (first == null) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addLast(value);
        } else {
            addBefore(value, nodeOnIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T data : list) {
            add(data);
        }
    }

    @Override
    public T get(int index) {
        checkForIndex(index);
        return nodeOnIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkForIndex(index);
        T oldValue = nodeOnIndex(index).item;
        nodeOnIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkForIndex(index);
        return unlink(nodeOnIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> nodeEdited = first; nodeEdited != null; nodeEdited = nodeEdited.next) {
            if (nodeEdited.item == object || object != null && object.equals(nodeEdited.item)) {
                unlink(nodeEdited);
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

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private Node<T> nodeOnIndex(int index) {
        return (index < (size >> 1)) ? nodeOnOnePartSize(index) : nodeOnSecondPartSize(index);
    }

    private Node<T> nodeOnOnePartSize(int index) {
        checkForIndex(index);
        Node<T> nodeResearch = first;
        for (int i = 0; i < index; i++) {
            nodeResearch = nodeResearch.next;
        }
        return nodeResearch;
    }

    private Node<T> nodeOnSecondPartSize(int index) {
        checkForIndex(index);
        Node<T> nodeResearch = last;
        for (int i = size - 1; i > index; i--) {
            nodeResearch = nodeResearch.prev;
        }
        return nodeResearch;
    }

    private void checkForIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    private void addFirst(T value) {
        Node<T> reFirst = first;
        first = new Node<>(null, value, reFirst);
        if (reFirst == null) {
            last = first;
        } else {
            reFirst.prev = first;
        }
        size++;
    }

    private void addBefore(T value, Node<T> nodeFull) {
        Node<T> rePrev = nodeFull.prev;
        nodeFull.prev = new Node<>(rePrev, value, nodeFull);
        if (rePrev == null) {
            first = nodeFull.prev;
        } else {
            rePrev.next = nodeFull.prev;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> reLast = last;
        last = new Node<>(reLast, value, null);
        if (reLast == null) {
            first = last;
        } else {
            reLast.next = last;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T object = node.item;
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
        node.item = null;
        size--;
        return object;
    }
}
