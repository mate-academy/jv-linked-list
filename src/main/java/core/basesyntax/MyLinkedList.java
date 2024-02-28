package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Provided index is out of valid range";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
    }

    @Override
    public void add(T value, int index) {
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
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

    private void unlink(Node<T> nodeToUnlink) {
        if (nodeToUnlink.equals(this.head)) {
            head = nodeToUnlink.next;
        }
        if (nodeToUnlink.equals(this.tail)) {
            tail = nodeToUnlink.prev;
        }
        nodeToUnlink.prev = null;
        nodeToUnlink.next = null;
    }

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
        Node<T> resultNode = null;
        if (index < size / 2) {
            resultNode = head;
            for (int i = 0; i < index; i++) {
               resultNode = resultNode.next;
            }
        } else {
            resultNode = tail;
            for (int i = size - 1; i > index; i--) {
                resultNode = resultNode.prev;
            }
        }
        return  resultNode;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        T item;

        public  Node (Node<T> previousNode, T item, Node<T> nextNode) {
            this.prev = previousNode;
            this.next = nextNode;
            this.item = item;
        }
    }

}
