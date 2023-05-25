package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> oldTail = tail;
        tail = new Node<>(oldTail, value, null);
        if (oldTail == null) {
            head = tail;
        } else {
            oldTail.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> target = getNode(index);
            linkBefore(value, target);
        }
    }

    private void linkBefore(T value, Node<T> target) {
        Node<T> newNode = new Node<>(null, value, target);
        if (target == head) {
            head = newNode;
            size++;
            return;
        }
        Node<T> previousNode = target.prev;
        target.prev = newNode;
        previousNode.next = newNode;
        newNode.prev = previousNode;
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
        Node<T> currentNode = getNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        T target = getNode(index).value;
        remove(target);
        return target;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (node == head) {
            head = head.next;
            head.prev = null;
            size--;
            return;
        }
        if (node == tail) {
            node.prev.next = null;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        int middleSize = size / 2;
        if (middleSize < index) {
            currentNode = tail;
            for (int j = size() - 1; j > index; j--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int j = 0; j < index; j++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index: "
                    + index
                    + " is bigger than or equals size: "
                    + size);
        }
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
        private Node<T> next;
        private T value;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
