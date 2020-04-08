package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {

        checkIndex(index, size);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nextNode = getNode(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        nextNode.prev = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T el : list) {
            add(el);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index, size - 1);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> node = getNode(index);
        T oldVal = node.element;
        node.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        Node<T> removedNode = getNode(index);
        Node<T> beforeNode = removedNode.prev;
        Node<T> afterNode = removedNode.next;
        if (beforeNode == null) {
            first = afterNode;
        } else {
            beforeNode.next = afterNode;
        }
        if (afterNode == null) {
            last = beforeNode;
        } else {
            afterNode.prev = beforeNode;
        }
        size--;
        return removedNode.element;
    }

    @Override
    public boolean remove(T remove) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            T el = node.element;
            if (remove == null ? el == null : el.equals(remove)) {
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

    private void checkIndex(int index, int condition) {
        if (index < 0 || index > condition) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        int from = index < (size >> 1) ? 0 : index;
        int to = index < (size >> 1) ? index : size - 1;
        Node<T> node = from == 0 ? first : last;
        for (int i = from; i < to; i++) {
            node = from == 0 ? node.next : node.prev;
        }
        return node;
    }

    private static class Node<E> {

        public E element;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
