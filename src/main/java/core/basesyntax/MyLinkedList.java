package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        linkLastElement(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIfIndexIsNotOutOfBound(index, size);
        if (index == size) {
            linkLastElement(value);
        } else {
            linkBeforeElement(value, getNodeByIndex(index));
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
        checkIfIndexIsNotOutOfBound(index, size - 1);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexIsNotOutOfBound(index, size - 1);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexIsNotOutOfBound(index, size - 1);
        Node<T> nodeToRemove = getNodeByIndex(index);
        Node<T> prevNode = nodeToRemove.prev;
        Node<T> nextNode = nodeToRemove.next;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return nodeToRemove.element;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            T element = node.element;
            if (t == null ? element == null : element.equals(t)) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private void checkIfIndexIsNotOutOfBound(int index, int size) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index : " + index + ", size : " + size);
        }
    }

    private void linkLastElement(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBeforeElement(T value, Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        node.prev = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
