package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = head;
            newNode.prev = null;
            head.prev = newNode;
            head = newNode;
        } else {
            newNode.next = getNodeByIndex(index);
            newNode.prev = newNode.next.prev;
            newNode.next.prev = newNode;
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> originNode = getNodeByIndex(index);
        T originNodeValue = originNode.value;
        originNode.value = value;
        return originNodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> originNode = getNodeByIndex(index);
        T originNodeValue = originNode.value;
        unLink(originNode, index);
        return originNodeValue;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object != null && object.equals(get(i)) || get(i) == object) {
                remove(i);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Inappropriate index: "
                    + index + " for size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index > size / 2) {
            Node<T> temp = tail;
            for (int i = 0; i < size - index - 1; i++) {
                temp = temp.prev;
            }
            return temp;
        }
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private void unLink(Node<T> node, int index) {
        if (index == 0) {
            head = head.next;
        }
        if (index == size - 1) {
            tail = tail.prev;
        }
        if (index > 0 && index < size - 1) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
