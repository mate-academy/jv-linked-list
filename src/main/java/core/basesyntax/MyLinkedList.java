package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(last, value, null);
        Node<T> oldNode = last;
        last = node;
        if (size == 0) {
            first = node;
        } else {
            oldNode.next = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (size == 0 || size == index) {
            add(value);
            return;
        }
        Node<T> oldNode = searchNode(index);
        Node<T> prevNode = oldNode.prev;
        Node<T> node = new Node<>(prevNode, value, oldNode);
        if (prevNode == null) {
            first = node;
        } else {
            prevNode.next = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkOutIndex(index);
        return searchNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkOutIndex(index);
        T oldValue = searchNode(index).value;
        searchNode(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkOutIndex(index);
        Node<T> removeNode = searchNode(index);
        Node<T> prevNode = removeNode.prev;
        Node<T> nextNode = removeNode.next;
        final T removeValue = searchNode(index).value;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = removeNode.next;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = removeNode.prev;
        }
        size--;
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = first;
        int i = 0;
        while (i < size) {
            if ((removeNode.value == null && object == null) || removeNode.value.equals(object)) {
                remove(i);
                return true;
            }
            removeNode = removeNode.next;
            i++;
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

    private void checkOutIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
    }

    private Node<T> searchNode(int index) {
        Node<T> searchNode = first;
        int i = 0;
        while (i < index) {
            searchNode = searchNode.next;
            i++;
        }
        return searchNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
