package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Can't execute this operation with index" + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> node = new Node<>(null, value, null);
            head.prev = node;
            node.next = head;
            head = node;
        } else {
            Node<T> nextNode = getNodeByIndex(index);
            Node<T> node = new Node<>(nextNode.prev, value, nextNode);
            nextNode.prev.next = node;
            nextNode.prev = node;
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
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> setNode = getNodeByIndex(index);
        T oldValue = setNode.item;
        setNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedValue = getNodeByIndex(index);
        unlink(removedValue);
        return removedValue.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove;
        for (nodeToRemove = head; nodeToRemove != null; nodeToRemove = nodeToRemove.next) {
            if (nodeToRemove.item == object
                    || (nodeToRemove.item != null && nodeToRemove.item.equals(object))) {
                unlink(nodeToRemove);
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
        Node<T> currentNode;
        if (index < size << 1) {
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

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Can't execute this operation with index" + index);
        }
    }

    private void unlink(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        size--;
        if (previousNode == null) {
            head = node.next;
        } else {
            previousNode.next = nextNode;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            nextNode.prev = previousNode;
        }
    }
}
