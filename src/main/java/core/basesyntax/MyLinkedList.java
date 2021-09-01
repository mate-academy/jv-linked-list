package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        } else {
            Node<T> node = getNode(index);
            insertNewNoda(value, node);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T temp : list) {
            add(temp);
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
        T oldValue = getNode(index).value;
        getNode(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeForRemove = getNode(index);
        unlink(nodeForRemove);
        size--;
        return nodeForRemove.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = head; i != null; i = i.next) {
            if (object == i.value || object != null && object.equals(i.value)) {
                unlink(i);
                size--;
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

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The index is not correct!");
        }
    }

    private void checkIndexForAdd(int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException("The index is not correct!");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        int count;
        if (index > size / 2) {
            count = size - 1;
            node = tail;
            while (index < count) {
                node = node.prev;
                count--;
            }
        } else {
            count = 0;
            node = head;
            while (count < index) {
                node = node.next;
                count++;
            }
        }
        return node;
    }

    private void insertNewNoda(T value, Node<T> node) {
        Node<T> preNode = node.prev;
        Node<T> nextNode = node.next;
        Node<T> newNode = new Node<>(preNode, value, node);
        if (preNode == null) {
            head = newNode;
        } else {
            preNode.next = newNode;
        }
        node.prev = newNode;
    }

    private void unlink(Node<T> node) {
        Node<T> preNode = node.prev;
        Node<T> nextNode = node.next;
        if (preNode == null && nextNode == null) {
            head = null;
            tail = null;
            return;
        }
        if (preNode == null) {
            nextNode.prev = null;
            head = nextNode;
            return;
        }
        if (nextNode == null) {
            preNode.next = null;
            tail = preNode;
            return;
        }
        preNode.next = nextNode;
        nextNode.prev = preNode;

    }
}
