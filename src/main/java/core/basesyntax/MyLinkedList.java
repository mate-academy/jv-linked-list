package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> nodeOne = new Node<>(tail,value, null);
        if (head == null) {
            head = nodeOne;
        } else {
            tail.next = nodeOne;
            nodeOne.prev = tail;
        }
        tail = nodeOne;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> tempNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(tempNode.prev, value, tempNode);
        if (tempNode.prev == null) {
            head = newNode;
        } else {
            tempNode.prev.next = newNode;
        }
        tempNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> changeNode = getNodeByIndex(index);
        T prevValue = changeNode.value;
        changeNode.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> tempNode = getNodeByIndex(index);
        T removeValue = tempNode.value;
        unlinkNode(tempNode);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((node.value != null && node.value.equals(object)) || node.value == object) {
                unlinkNode(node);
                return true;
            }
            node = node.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Out Of Bounds: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void unlinkNode(Node<T> current) {
        Node<T> prev = current.prev;
        Node<T> next = current.next;
        if (current == head) {
            head = next;
        } else if (current == tail) {
            tail = prev;
        } else {
            prev.next = next;
            next.prev = prev;
        }
        size--;
    }
}
