package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node(Node<T> prev, T value) {
            this.prev = prev;
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> indexNode = getNodeAtIndex(index);
            Node<T> prevToIndexNode = indexNode.prev;
            Node<T> newNode = new Node<>(prevToIndexNode, value, indexNode);
            if (index == 0) {
                head = newNode;
            } else {
                prevToIndexNode.next = newNode;
            }
            indexNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        indexRangeCheck(index);
        return getNodeAtIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexRangeCheck(index);
        Node<T> indexNode = getNodeAtIndex(index);
        T oldValue = indexNode.value;
        indexNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexRangeCheck(index);
        Node<T> removedNode = getNodeAtIndex(index);
        unlink(removedNode);
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        int index = 0;
        while (temp != null) {
            if (object == temp.value
                    || temp.value != null && temp.value.equals(object)) {
                remove(index);
                return true;
            }
            temp = temp.next;
            index++;
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

    private void indexRangeCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index);
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        indexRangeCheck(index);
        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = head;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = tail;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }
}
