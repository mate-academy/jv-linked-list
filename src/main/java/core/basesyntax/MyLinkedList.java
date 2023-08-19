package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<T> newNode = new Node<>(value);
        if (index == size) {
            insertLast(newNode);
        } else if (index == 0) {
            insertFirst(newNode);
        } else {
            Node<T> currentNode = getNodeAtIndex(index);
            Node<T> prevNode = currentNode.getPrev();
            insertNode(newNode, currentNode, prevNode);
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeAtIndex(index);
        return node.getData();
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeAtIndex(index);
        T oldValue = node.getData();
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeAtIndex(index);
        unlink(node);
        size--;
        return node.getData();
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (object == null
                    ? currentNode.getData() == null : object.equals(currentNode.getData())) {
                unlink(currentNode);
                size--;
                return true;
            }
            currentNode = currentNode.getNext();
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

    private void insertNode(Node<T> newNode, Node<T> currentNode, Node<T> prevNode) {
        prevNode.setNext(newNode);
        newNode.setPrev(prevNode);
        newNode.setNext(currentNode);
        currentNode.setPrev(newNode);
    }

    private Node<T> getNodeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.getPrev();
        Node<T> nextNode = node.getNext();

        if (prevNode != null) {
            prevNode.setNext(nextNode);
        } else {
            first = nextNode;
        }

        if (nextNode != null) {
            nextNode.setPrev(prevNode);
        } else {
            last = prevNode;
        }
    }

    private void insertFirst(Node<T> newNode) {
        newNode.setNext(first);
        if (first != null) {
            first.setPrev(newNode);
        } else {
            last = newNode;
        }
        first = newNode;
    }

    private void insertLast(Node<T> newNode) {
        newNode.setPrev(last);
        if (last != null) {
            last.setNext(newNode);
        } else {
            first = newNode;
        }
        last = newNode;
    }
}
