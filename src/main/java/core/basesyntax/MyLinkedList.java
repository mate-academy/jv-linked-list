package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> previousNode = getNodeByIndex(index).prev;
            Node<T> newNode = new Node<>(previousNode, value, getNodeByIndex(index));
            getNodeByIndex(index).prev = newNode;
            if (previousNode == null) {
                head = newNode;
            } else {
                previousNode.next = newNode;
            }
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T valueOldNode = currentNode.value;
        currentNode.value = value;
        return valueOldNode;
    }

    @Override
    public T remove(int index) {
        Node<T> targetNode = getNodeByIndex(index);
        removeNodeLink(targetNode);
        return targetNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> targetNode = head;
        while (targetNode != null) {
            if (targetNode.value == object || targetNode.value != null
                    && targetNode.value.equals(object)) {
                removeNodeLink(targetNode);
                return true;
            }
            targetNode = targetNode.next;
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

    private class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index: " + index
                    + " should be between 0 and size: " + size);
        }
        if (index < (size / 2)) {
            Node<T> nearHead = head;
            for (int i = 0; i < index; i++) {
                nearHead = nearHead.next;
            }
            return nearHead;
        } else {
            Node<T> nearTail = tail;
            for (int i = size - 1; i > index; i--) {
                nearTail = nearTail.prev;
            }
            return nearTail;
        }
    }

    private void removeNodeLink(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
