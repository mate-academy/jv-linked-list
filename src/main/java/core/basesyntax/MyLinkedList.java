package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(null, value, null);
        if (head == null) {
            head = node;
        } else {
            node.previous = tail;
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        throwException(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> valueNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(valueNode.previous, value, valueNode);
        if (valueNode.previous == null) {
            head = newNode;
        } else {
            valueNode.previous.next = newNode;
        }
        valueNode.previous = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        throwException(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        throwException(index);
        Node<T> node = getNodeByIndex(index);
        T oldVal = node.value;
        node.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        throwException(index);
        Node<T> removeTarget = getNodeByIndex(index);
        unlink(removeTarget);
        return removeTarget.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNodeByValue(object);
        if (node == null) {
            return false;
        }
        unlink(node);
        return true;
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
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    private void throwException(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds for length");
        }
    }

    private Node<T> getNodeByIndex(int targetIndex) {
        throwException(targetIndex + 1);
        Node<T> currentNode;
        if (targetIndex < size >> 1) {
            currentNode = head;
            while (targetIndex > 0) {
                currentNode = currentNode.next;
                targetIndex--;
            }
        } else {
            currentNode = tail;
            while (targetIndex < size - 1) {
                currentNode = currentNode.previous;
                targetIndex++;
            }
        }
        return currentNode;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> newNode = head;
        while (newNode != null) {
            if (newNode.value == value || newNode.value != null && newNode.value.equals(value)) {
                return newNode;
            }
            newNode = newNode.next;
        }
        return null;
    }

    private void unlink(Node<T> removeNode) {
        Node<T> nodePrev = removeNode.previous;
        Node<T> nodeNext = removeNode.next;
        if (nodePrev == null) {
            head = nodeNext;
        } else {
            nodePrev.next = nodeNext;
        }
        if (nodeNext == null) {
            tail = nodePrev;
        } else {
            nodeNext.previous = nodePrev;
        }
        size--;
    }
}
