package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            add(value);
        } else {
            Node<T> curr = getNode(index);
            Node<T> newNode = new Node<>(curr.prev, value, curr);
            curr.prev.next = newNode;
            curr.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = getNode(index);
        final T value = node.value;
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
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = new Node<>(null, object, null);
        int temp = 0;
        for (Node<T> i = head; i != null; i = i.next) {
            temp++;
            if (nodeToRemove.value == i.value
                    || nodeToRemove.value != null && nodeToRemove.value.equals(i.value)) {
                removeNode(temp - 1);
                return true;
            }
        }
        return false;
    }

    private T removeNode(int index) {
        T removedItem;
        if (index == 0) {
            removedItem = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> removedNode = getNode(index);
            removedItem = removedNode.value;
            if (index == size - 1) {
                tail = removedNode.prev;
            } else {
                removedNode.prev.next = removedNode.next;
                removedNode.next.prev = removedNode.prev;
            }
        }
        size--;
        return removedItem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
