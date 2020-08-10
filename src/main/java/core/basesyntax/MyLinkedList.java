package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size = 0;
    private transient Node<T> first;
    private transient Node<T> last;

    @Override
    public boolean add(T value) {
        addElement(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Non-valid index");
        } else if (index > 0 && index < size) {
            Node<T> oldNode = nodeByIndex(index);
            Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
            size++;
        } else if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            addElement(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        check(index);
        return nodeByIndex(index).data;
    }

    @Override
    public T set(T value, int index) {
        check(index);
        Node<T> nodeIndex = nodeByIndex(index);
        T oldData = nodeIndex.data;
        nodeIndex.data = value;
        return oldData;
    }

    @Override
    public T remove(int index) {
        check(index);
        Node<T> removableNode = nodeByIndex(index);
        T data = removableNode.data;
        if (index == 0) {
            if (size == 1) {
                first = null;
                last = null;
            } else {
                first = removableNode.next;
                removableNode.next.prev = null;
            }
        } else if (index > 0 && index < size - 1) {
            removableNode.prev.next = removableNode.next;
            removableNode.next.prev = removableNode.prev;
        } else if (index == size - 1) {
            last = removableNode.prev;
            last.next = null;
        }
        size--;
        return data;
    }

    @Override
    public boolean remove(T t) {
        for (int index = 0; index < size; index++) {
            Node<T> removableNode = nodeByIndex(index);
            if (t == removableNode.data
                    || t != null && t.equals(removableNode.data)) {
                remove(index);
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

        T data;
        Node<T> next;
        Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.data = element;
            this.next = next;
        }
    }

    private void check(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Non-valid index");
        }
    }

    private Node<T> nodeByIndex(int index) {
        check(index);
        Node<T> resultNode;
        if (index > size / 2) {
            resultNode = last;
            for (int i = size - 1; i > index; i--) {
                resultNode = resultNode.prev;
            }
        } else {
            resultNode = first;
            for (int i = 0; i < index; i++) {
                resultNode = resultNode.next;
            }
        }
        return resultNode;
    }

    private void addElement(T value) {
        if (size == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    private void addFirst(T value) {
        Node<T> newFirst = new Node<>(null, value, null);
        if (size != 0) {
            newFirst.next = first;
            first.prev = newFirst;
        } else {
            last = newFirst;
        }
        first = newFirst;
        size++;
    }

    private void addLast(T value) {
        Node<T> newLast = new Node<>(null, value, null);
        last.next = newLast;
        newLast.prev = last;
        last = newLast;
        size++;
    }
}
