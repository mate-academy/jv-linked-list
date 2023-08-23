package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node head;
    private Node tail;

    @Override
    public void add(T value) {
        if (size < 2) {
            addHead(value);
            return;
        }

        Node node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index " + index + " is out of size " + size);
        }

        if (size == 0 || index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            addIfIndex0(value, index);
            return;
        }

        checkIndex(index);
        Node currentNode = head.next;
        Node indexNode = null;
        for (int i = 0; i < index; i++) {
            indexNode = currentNode;
            currentNode = indexNode.next;
        }

        Node insertNode = new Node(indexNode.prev, value, indexNode);
        indexNode.prev.next = insertNode;
        indexNode.prev = insertNode;

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node indexNode = getNode(index);
        T returnNode = (T)indexNode.value;
        indexNode.value = value;

        return returnNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node returnNode = getNode(index);
        unlink(returnNode);
        size--;

        return (T)returnNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;

        for (int i = 0; i < size; i++) {
            if (currentNode.value != null && currentNode.value.equals(object)
                    || currentNode.value == null && object == null) {
                unlink(currentNode);
                size--;
                return true;
            }
            currentNode = currentNode.next;
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

    private class Node<T> {
        private Node prev;
        private Node next;
        private T value;

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void unlink(Node node) {
        if (node.next == null && node.prev == null) {
            head = null;
            tail = null;
            return;
        }

        if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
            return;
        }

        if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
            return;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index "
                    + index + " is equals or out of size " + size);
        }
    }

    private void addHead(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = new Node<>(null, value, null);
            size++;
            return;
        }
        Node node = new Node<>(head, value, null);
        tail = node;
        tail.prev = head;
        head.next = node;
        size++;
    }

    private void addIfIndex0(T value, int index) {
        Node node = new Node<>(null, value, head);
        head.prev = node;
        head = node;
        size++;
    }

    private Node <T> getNode(int index) {
        Node currentNode = head;
        Node indexNode = null;
        for (int i = 0; i <= index; i++) {
            indexNode = currentNode;
            currentNode = indexNode.next;
        }
        return indexNode;
    }
}
