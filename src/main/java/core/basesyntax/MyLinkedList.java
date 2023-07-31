package core.basesyntax;

import java.util.List;
import java.util.Objects;

Analyze code and make it better
Do comments, where needed, explaint, whats happening

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
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
        Node<T> newNode;
        if (index == size) {
            add(value);
        } else {
            if (index == 0) {
                newNode = new Node<>(null, value, head);
                head.prev = newNode;
                head = newNode;
            } else {
                Node<T> nextNode = getNode(index);
                newNode = new Node<T>(nextNode.prev, value, nextNode);
                nextNode.prev.next = newNode;
                nextNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        int count = 0;
        Node<T> node = head;
        while (count != index) {
            node = node.next;
            count++;
        }
        return node;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T replacedValue = node.value;
        node.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNode(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.value, object)) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Non-existent list index");
        }
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode == head) {
            head = currentNode.next;
        } else if (currentNode == tail) {
            tail = currentNode.prev;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
