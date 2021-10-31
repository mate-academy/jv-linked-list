package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    static class Node<T> {
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
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        if (index == 0 && size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(getNodeByIndex(index).prev, value, getNodeByIndex(index));
            getNodeByIndex(index).prev.next = newNode;
            getNodeByIndex(index).prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
        if (size == list.size()) {
            head = tail = (Node<T>) list.get(0);
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        if (index == 0) {
            return head.value;
        } else if (index == size - 1) {
            return tail.value;
        } else {
            return getNodeByIndex(index).value;
        }
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> replaceNode = getNodeByIndex(index);
        T deletedValue = replaceNode.value;
        replaceNode.value = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        if (size == 0) {
            return null;
        }
        Node<T> deletedNode = getNodeByIndex(index);
        unLink(getNodeByIndex(index));
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> deletedNode;
        for (deletedNode = head; deletedNode != null; deletedNode = deletedNode.next) {
            if (object == deletedNode.value
                    || object != null && object.equals(deletedNode.value)) {
                unLink(deletedNode);
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

    public Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index > (size >> 1)) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    public void unLink(Node<T> node) {
        if (size == 1) {
            tail = head = null;
        } else if (node.next == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else if (node.prev == null) {
            head.next.prev = null;
            head = head.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
