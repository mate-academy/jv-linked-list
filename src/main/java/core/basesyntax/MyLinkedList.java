package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        }
        if (head == null) {
            head = newNode;
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
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
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
        Node<T> currentNode = getNode(index);
        return currentNode.value1;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        T currentValue = currentNode.value1;
        currentNode.value1 = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {

        checkIndex(index);
        Node<T> currentNode = getNode(index);
        removedLink(currentNode);
        return currentNode.value1;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value1 == object || object != null && object.equals(node.value1)) {
                removedLink(node);
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

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index " + index + "  is invalid");
        }
    }

    public Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < (size / 2)) {
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

    public void removedLink(Node<T> removedNode) {
        if (removedNode == head) {
            head = removedNode.next;
        } else if (removedNode == tail) {
            tail = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
            removedNode.prev.next = removedNode.next;
        }
        size--;
    }

    private static class Node<T> {
        private T value1;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value1, Node<T> next) {
            this.prev = prev;
            this.value1 = value1;
            this.next = next;
        }
    }
}
