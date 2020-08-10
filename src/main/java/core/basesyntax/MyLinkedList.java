package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firsNode;
    private Node<T> lastNode;

    @Override
    public boolean add(T value) {
        addToLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> node = new Node<>(null, value, firsNode);
            firsNode.prev = node;
            firsNode = node;
            size++;
            return;
        }
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        if (size == 1) {
            lastNode = null;
            firsNode = null;
        } else if (index == 0) {
            node.next.prev = null;
            firsNode = node.next;
        } else if (index == size - 1) {
            node.prev.next = null;
            lastNode = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node.element;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).element != null && getNode(i).element.equals(t)
                    || t == getNode(i).element) {
                remove(i);
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
        T element;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;

        }
    }

    private void addToLast(T value) {
        if (isEmpty()) {
            Node<T> newNode = new Node<>(null, value, null);
            firsNode = lastNode = newNode;
        } else {
            Node<T> newNode = new Node<>(lastNode, value, null);
            lastNode.next = newNode;
            lastNode = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index < (size >> 1)) {
            int i = 0;
            Node<T> node = firsNode;
            while (i < index) {
                node = node.next;
                i++;
            }
            return node;
        } else {
            int i = 0;
            Node<T> node = firsNode;
            while (i < index) {
                node = node.next;
                i++;
            }
            return node;
        }
    }
}
