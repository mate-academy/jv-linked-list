package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    public static class Node<T> {
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
        Node<T> newNode = new Node<>(null, value, null);
        size++;
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        checkEqualsSizeIndex(index);
        if (index == 0) {
            if (isEmpty()) {
                add(value);
            } else {
                Node<T> newNode = new Node<>(null, value, head);
                head.prev = newNode;
                head = newNode;
                size++;
            }
        } else {
            Node<T> variousNode = head;
            for (int a = 1; a < index; a++) {
                variousNode = variousNode.next;
            }
            if (variousNode == tail) {
                add(value);
            } else {
                Node<T> followingNode = variousNode.next;
                Node<T> newNode = new Node(variousNode, value, followingNode);
                variousNode.next = newNode;
                followingNode.prev = newNode;
                size++;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> previousNode = tail;
        if (list.size() > 0) {
            Node<T> firstListNode = new Node(previousNode, list.get(0), null);
            previousNode.next = firstListNode;
            Node<T> variableNode = firstListNode;
            Node<T> newNode;
            for (int a = 1; a < list.size(); a++) {
                newNode = new Node(variableNode, list.get(a), null);
                variableNode.next = newNode;
                variableNode = newNode;
            }
            tail = variableNode;
            size += list.size();
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> variableNode = head;
        for (int a = 1; a <= index; a++) {
            variableNode = variableNode.next;
        }
        return variableNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T deletedValue;
        Node<T> variableNode = head;
        for (int a = 1; a <= index; a++) {
            variableNode = variableNode.next;
        }
        deletedValue = variableNode.value;
        variableNode.value = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T deletedValue = null;
        if (index == 0 && size > 1) {
            deletedValue = head.value;
            head = head.next;
        }
        if (size == 1) {
            deletedValue = head.value;
            head = null;
            tail = null;
        }
        if (index == size - 1 && index > 0) {
            deletedValue = tail.value;
            Node<T> lastNode = tail;
            lastNode = lastNode.prev;
            tail = lastNode;
            lastNode.next = null;
        }
        if (size - 1 > index && size > 1 && index != 0) {
            Node<T> deletedNode = head;
            for (int a = 1; a <= index; a++) {
                deletedNode = deletedNode.next;
            }
            deletedValue = deletedNode.value;
            Node<T> previousNode = deletedNode.prev;
            Node<T> followingNode = deletedNode.next;
            previousNode.next = followingNode;
            followingNode.prev = previousNode;
        }
        size--;
        return deletedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> variableNode = head;
        int oldSize = size;
        for (int a = 0; a < oldSize; a++) {
            if ((variableNode.value == null && object == null)
                    || (variableNode.value != null && variableNode.value.equals(object))) {
                remove(a);
                break;
            } else {
                variableNode = variableNode.next;
            }
        }
        return size != oldSize;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("this index is not correction");
        }
    }

    private void checkEqualsSizeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("this index is not correction");
        }
    }
}

