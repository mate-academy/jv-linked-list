package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> currentNode = new Node<>(tail, value, null);
            tail.next = currentNode;
            tail = currentNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> currentNode = new Node<>(null, value, head);
            head.prev = currentNode;
            head = currentNode;
        } else {
            Node<T> currentNode = nodeFromIndex(index);
            Node<T> node = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = node;
            currentNode.prev = node;
        }
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
        return nodeFromIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = nodeFromIndex(index);
        T returnValue = currentNode.value;
        currentNode.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = nodeFromIndex(index);
        return unLink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (object == current.value || (object != null && object.equals(current.value))) {
                unLink(current);
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

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounds for size: " + size);
        }
    }

    private Node<T> nodeFromIndex(int index) {
        indexValidation(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int j = size - 1; j > index; j--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unLink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else if (node.next == null) {
            tail = node.prev;
            node.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node.value;
    }

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
}
