package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else if (tail.next == null) {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of size: " + size);
        }
        if (index == 0 && head == null || index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> indexNode = getNodeByIndex(index - 1);
        indexNode.next = new Node<>(indexNode, value, indexNode.next);
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
        if (isEmpty()) {
            return null;
        }
        Node<T> result = getNodeByIndex(index);
        return result.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        if (isEmpty()) {
            return null;
        }
        Node<T> indexNode = getNodeByIndex(index);
        T result = indexNode.value;
        indexNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (isEmpty()) {
            return null;
        }
        if (index == 0) {
            return removeFirst();
        }
        Node<T> indexNode = getNodeByIndex(index);
        unlink(indexNode);
        return indexNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> indexNode = head;
        while (indexNode != null) {
            if (indexNode.value == object || indexNode.value != null
                    && indexNode.value.equals(object)) {
                unlink(indexNode);
                return true;
            }
            indexNode = indexNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of size: " + size);
        }
    }

    private T removeFirst() {
        T result = head.value;
        size--;
        if (head.next != null) {
            head = head.next;
            head.prev = null;
            return result;
        } else {
            head = null;
            return null;
        }
    }

    private void unlink(Node<T> node) {
        if (head == node) {
            removeFirst();
            return;
        }
        node.prev.next = node.next;
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> indexNode = head;
        for (int i = 0; i < index; i++) {
            indexNode = indexNode.next;
        }
        return indexNode;
    }
}
