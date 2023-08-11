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
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            Node<T> nextNode = prevNode.next;
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
        Node<T> removedNode = getNodeByIndex(index);
        T value = removedNode.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head = removedNode.next;
            head.prev = null;
        } else if (index == size - 1) {
            tail = removedNode.prev;
            tail.next = null;
        } else {
            Node<T> prev = removedNode.prev;
            Node<T> next = removedNode.next;
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
            if (nodeToRemove.value == object || (nodeToRemove.value
                    != null && nodeToRemove.value.equals(object))) {
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
            throw new IndexOutOfBoundsException("Index:"
                    + index + " is bigger than linked list size!");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void addFirst(T value) {
        Node<T> newHead = new Node<>(null, value, head);
        if (head != null) {
            head.prev = newHead;
        }
        head = newHead;
        if (tail == null) {
            tail = newHead;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> newTail = new Node<>(tail, value, null);
        if (tail != null) {
            tail.next = newTail;
        }
        tail = newTail;
        if (head == null) {
            head = newTail;
        }
        size++;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
