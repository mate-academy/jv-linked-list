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
            this.add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, first.nextNode, value);
            first.prevNode = newNode;
            first = newNode;
            size++;
            return;
        }
        this.validIndex(index - 1);
        if (index != size) {
            Node<T> node = this.getIndexedNode(index);
            Node<T> newNode = new Node<>(node.prevNode, node.nextNode, value);
            node.prevNode = newNode;
            node = newNode.prevNode;
            node.nextNode = newNode;
            size++;
            return;
        }
        this.add(value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            this.add(value);
        }
    }

    @Override
    public T get(int index) {
        this.validIndex(index);
        return this.getIndexedNode(index).value;
    }

    @Override
    public void set(T value, int index) {
        this.validIndex(index);
        this.getIndexedNode(index).value = value;
    }

    @Override
    public T remove(int index) {
        this.validIndex(index);
        if (index == 0 && size == 1) {
            first = null;
            Node<T> node = this.getIndexedNode(index);
            last = null;
            size--;
            return node.value;
        }
        if (index == 0) {
            Node<T> node = this.getIndexedNode(index);
            node.nextNode.prevNode = null;
            first = node.nextNode;
            size--;
            return node.value;
        }
        if (index == size - 1) {
            Node<T> node = this.getIndexedNode(index);
            node.prevNode.nextNode = null;
            last = node.prevNode;
            size--;
            return node.value;
        }
        Node<T> node = this.getIndexedNode(index);
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
                    this.remove(i);
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
