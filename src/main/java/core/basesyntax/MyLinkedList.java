package core.basesyntax;

import java.util.List;

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
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException("Illegal Index: " + index);
        }
    }

    private Node<T> indexedNode(int index) {
        Node<T> node;
        if (index < this.size / 2) {
            node = this.first;
            for (int i = 0; i < index; i++) {
                node = node.nextNode;
            }
            return node;
        }
        node = this.last;
        for (int i = this.size - 1; i > index; i--) {
            node = node.prevNode;
        }
        return node;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node0 = new Node<>(value);
            this.first = node0;
            this.last = node0;
            this.size++;
            return;
        }
        Node<T> node = new Node<>(last, null, value);
        this.last.nextNode = node;
        this.last = node;
        this.size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0 && this.size == 0) {
            this.add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, this.first.nextNode, value);
            this.first.prevNode = newNode;
            this.first = newNode;
            this.size++;
            return;
        }
        this.validIndex(index - 1);
        if (index != this.size) {
            Node<T> node = this.indexedNode(index);
            Node<T> newNode = new Node<>(node.prevNode, node.nextNode, value);
            node.prevNode = newNode;
            node = newNode.prevNode;
            node.nextNode = newNode;
            this.size++;
            return;
        }
        this.add(value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T i : list) {
            this.add(i);
        }
    }

    @Override
    public T get(int index) {
        this.validIndex(index);
        return this.indexedNode(index).value;
    }

    @Override
    public void set(T value, int index) {
        this.validIndex(index);
        if (index == 0) {
            this.first.value = value;
            return;
        }
        if (index == this.size - 1) {
            this.last.value = value;
            return;
        }
        this.indexedNode(index).value = value;
    }

    @Override
    public T remove(int index) {
        this.validIndex(index);
        if (index == 0 && this.size == 1) {
            Node<T> node = this.indexedNode(index);
            this.last = null;
            this.first = null;
            T value = node.value;
            this.size--;
            return value;
        }
        if (index == 0) {
            Node<T> nextNode = this.indexedNode(index).nextNode;
            Node<T> node = this.indexedNode(index);
            nextNode.prevNode = null;
            this.first = nextNode;
            T value = node.value;
            this.size--;
            return value;
        }
        if (index == this.size - 1) {
            Node<T> prevNode = this.indexedNode(index).prevNode;
            Node<T> node = this.indexedNode(index);
            prevNode.nextNode = null;
            this.last = prevNode;
            T value = node.value;
            this.size--;
            return value;
        }
        Node<T> prevNode = this.indexedNode(index).prevNode;
        Node<T> nextNode = this.indexedNode(index).nextNode;
        Node<T> node = this.indexedNode(index);
        prevNode.nextNode = nextNode;
        nextNode.prevNode = prevNode;
        T value = node.value;
        this.size--;
        return value;
    }

    @Override
    public T remove(T t) {
        if (this.size > 0) {
            Node<T> node = this.first;
            for (int i = 0; i < size; i++) {
                if (node.value == t || t.equals(node.value)) {
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
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size == 0);
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
