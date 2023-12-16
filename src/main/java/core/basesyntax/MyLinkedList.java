package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        size++;
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            newNode.next.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> node = getNodeAtIndex(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeAtIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeAtIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNodeAtIndex(index);
        unlink(target);
        return target.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.value || (object != null
                    && object.equals(currentNode.value))) {
                unlink(currentNode);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds!");
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        size--;
        if (size == 0) {
            node.value = null;
            return;
        }
        if (isHead(node)) {
            node.next.prev = null;
            head = node.next;
            return;
        } else if (isTail(node)) {
            node.prev.next = null;
            tail = node.prev;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private boolean isHead(Node<T> node) {
        return head == node;
    }

    private boolean isTail(Node<T> node) {
        return tail == node;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
