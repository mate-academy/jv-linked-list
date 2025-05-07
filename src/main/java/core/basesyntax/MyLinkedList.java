package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexCheck(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        Node<T> nextNode = getNodeByIndex(index);
        Node<T> prevNode = nextNode.prev;
        newNode.next = nextNode;
        newNode.prev = prevNode;
        prevNode.next = newNode;
        nextNode.prev = newNode;
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
        indexCheck(index);
        Node<T> node = getNodeByIndex(index);
        return node == null ? null : node.element;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        return unlink(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if (object == nodeToRemove.element
                    || object != null && object.equals(nodeToRemove.element)) {
                unlink(nodeToRemove);
                return true;
            }
            nodeToRemove = nodeToRemove.next;
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

    private T unlink(Node<T> nodeToRemove) {
        if (nodeToRemove == null) {
            return null;
        }
        if (nodeToRemove.prev == null || nodeToRemove.next == null) {
            return deleteFirstOrLastNode(nodeToRemove);
        }
        return deleteNode(nodeToRemove);
    }

    private T deleteFirstOrLastNode(Node<T> nodeToRemove) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (nodeToRemove.next == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            head.next.prev = null;
            head = head.next;
        }
        size--;
        return nodeToRemove.element;
    }

    private T deleteNode(Node<T> nodeToRemove) {
        nodeToRemove.next.prev = nodeToRemove.prev;
        nodeToRemove.prev.next = nodeToRemove.next;
        size--;
        return nodeToRemove.element;
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByIndex(int index) {
        return index > size / 2 ? goFromTailToHead(index) : goFromHeadToTail(index);
    }

    private Node<T> goFromTailToHead(int index) {
        Node<T> currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private Node<T> goFromHeadToTail(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
