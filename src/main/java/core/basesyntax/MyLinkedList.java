package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            checkNodeNext(newNode);
            head = newNode;
            size++;
            return;
        }
        Node<T> currentNode = getNodeByIndex(index - 1);
        Node<T> nextNode = currentNode.next;
        newNode.next = nextNode;
        checkNodeNext(newNode);
        if (nextNode != null) {
            nextNode.prev = newNode;
        }
        newNode.prev = currentNode;
        currentNode.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List is null");
        }
        for (T type : list) {
            add(type);
        }
    }

    @Override
    public T get(int index) {
        checkTheIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkTheIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T valueToChange = currentNode.item;
        currentNode.item = value;
        return valueToChange;
    }

    @Override
    public T remove(int index) {
        checkTheIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        T removedValue = nodeToRemove.item;
        unlink(nodeToRemove);
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Trying to remove an element from an empty list");
        }
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if ((object == null && nodeToRemove.item == null)
                    || (nodeToRemove.item != null && nodeToRemove.item.equals(object))) {
                unlink(nodeToRemove);
                size--;
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

    private void checkNodeNext(Node<T> node) {
        if (node.next == null) {
            tail = node;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (size / 2 > index) {
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

    private void unlink(Node node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode != null) {
            prevNode.next = node.next;
        } else {
            head = node.next;
        }
        if (nextNode != null) {
            nextNode.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private void checkTheIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }
}
