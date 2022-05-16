package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
            size++;
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
            size++;
        } else {
            Node<T> oldNodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(oldNodeByIndex.prev, value, oldNodeByIndex);
            oldNodeByIndex.prev.next = newNode;
            oldNodeByIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNodeByIndex = getNodeByIndex(index);
        T element = oldNodeByIndex.item;
        oldNodeByIndex.item = value;
        return element;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (Node i = currentNode; i != tail.next; i = i.next) {
            if (i.item == object || (i.item != null && i.item.equals(object))) {
                unlink(i);
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
        checkIndex(index);
        int count = 0;
        Node<T> currentNode;
        if (index < (size / 2)) {
            currentNode = head;
            while (count != index) {
                currentNode = currentNode.next;
                count++;
            }
            return currentNode;
        } else {
            currentNode = tail;
            count = size - 1;
            while (count != index) {
                currentNode = currentNode.prev;
                count--;
            }
            return currentNode;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            if (size == 1) {
                head = null;
            } else {
                node.next.prev = null;
                head = node.next;
            }
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node prev;
        private Node next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
