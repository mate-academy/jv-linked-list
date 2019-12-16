package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    private void validIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Illegal Index: " + index);
        }
    }

    private Node<T> getIndexedNode(int index) {
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.nextNode;
            }
            return node;
        }
        node = last;
        for (int i = size - 1; i > index; i--) {
            node = node.prevNode;
        }
        return node;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(value);
            first = node;
            last = node;
            size++;
            return;
        }
        Node<T> node = new Node<>(last, null, value);
        last.nextNode = node;
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0 && size == 0) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, first.nextNode, value);
            first.prevNode = newNode;
            first = newNode;
            size++;
            return;
        }
        validIndex(index - 1);
        if (index != size) {
            Node<T> node = getIndexedNode(index);
            Node<T> newNode = new Node<>(node.prevNode, node.nextNode, value);
            node.prevNode = newNode;
            node = newNode.prevNode;
            node.nextNode = newNode;
            size++;
            return;
        }
        add(value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validIndex(index);
        return getIndexedNode(index).value;
    }

    @Override
    public void set(T value, int index) {
        validIndex(index);
        getIndexedNode(index).value = value;
    }

    @Override
    public T remove(int index) {
        validIndex(index);
        Node<T> node = getIndexedNode(index);
        if (index == 0 && size == 1) {
            first = null;
            last = null;
            size--;
            return node.value;
        }
        if (index == 0) {
            node.nextNode.prevNode = null;
            first = node.nextNode;
            size--;
            return node.value;
        }
        if (index == size - 1) {
            node.prevNode.nextNode = null;
            last = node.prevNode;
            size--;
            return node.value;
        }
        node.prevNode.nextNode = node.nextNode;
        node.nextNode.prevNode = node.prevNode;
        size--;
        return node.value;
    }

    @Override
    public T remove(T t) {
        if (size > 0) {
            Node<T> node = first;
            for (int i = 0; i < size; i++) {
                if (Objects.equals(t, node.value)) {
                    remove(i);
                    return t;
                }
                node = node.nextNode;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private class Node<T> {

        private Node<T> prevNode;
        private Node<T> nextNode;
        private T value;

        public Node(T value) {
            this.prevNode = null;
            this.nextNode = null;
            this.value = value;
        }

        public Node(Node<T> prevNode, Node<T> nextNode, T value) {
            this.prevNode = prevNode;
            this.nextNode = nextNode;
            this.value = value;
        }
    }
}
