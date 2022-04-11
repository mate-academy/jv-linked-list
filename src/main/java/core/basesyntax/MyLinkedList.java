package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(oldTail, value, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && index <= size) {
            if (index == size) {
                add(value);
            } else {
                Node<T> next = getNodeByIndex(index);
                Node<T> prev = next.prev;
                Node<T> newNode = new Node<>(prev, value, next);
                next.prev = newNode;
                if (prev == null) {
                    head = newNode;
                } else {
                    prev.next = newNode;
                }
                size++;
            }
        } else {
            throw new IndexOutOfBoundsException("Can't add value. Pass correct input index");
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
        if (index >= 0 && index < size) {
            return getNodeByIndex(index).item;
        }
        throw new IndexOutOfBoundsException("Can't get item. Pass correct input index");
    }

    @Override
    public T set(T value, int index) {
        if (index >= 0 && index < size) {
            Node<T> targetNode = getNodeByIndex(index);
            T oldValue = targetNode.item;
            targetNode.item = value;
            return oldValue;
        }
        throw new IndexOutOfBoundsException("Can't set value. Pass correct input index");
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            Node<T> removeNode = getNodeByIndex(index);
            unlink(removeNode);
            return removeNode.item;
        }
        throw new IndexOutOfBoundsException("Can't remove item. Pass correct input index");
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == node.item || object != null && object.equals(node.item)) {
                unlink(node);
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> nodeByIndex;
        if (index < size / 2) {
            nodeByIndex = head;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
        } else {
            nodeByIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
        }
        return nodeByIndex;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
