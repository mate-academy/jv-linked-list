package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
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
        if (index == 0) {
            if (size == 0) {
                add(value);
            } else {
                Node<T> nextNode = head;
                Node<T> newNode = new Node<>(null, value, nextNode);
                head = newNode;
                nextNode.prev = newNode;
                size++;
            }
        } else if (index == size) {
            add(value);
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            Node<T> nextNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            prevNode.next = newNode;
            nextNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        T value = getNodeByIndex(index).value;
        if (size == 1) {
            tail = null;
            head = null;
        } else if (index == 0) {
            getNodeByIndex(index + 1).prev = null;
            head = getNodeByIndex(index + 1);
        } else if (index == size - 1) {
            getNodeByIndex(index - 1).next = null;
            tail = getNodeByIndex(index - 1);
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            Node<T> next = getNodeByIndex(index + 1);
            prev.next = next;
            next.prev = prev;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = head;
        int index = 0;
        while (nodeToRemove != null) {
            if (nodeToRemove.value == object
                    || (nodeToRemove.value != null && nodeToRemove.value.equals(object))) {
                remove(index);
                return true;
            }
            nodeToRemove = nodeToRemove.next;
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

    private Node<T> getNodeByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index can`t be bigger than linked list size!");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

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
}
