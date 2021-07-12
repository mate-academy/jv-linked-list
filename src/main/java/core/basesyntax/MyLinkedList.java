package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) {
            head = node;
        }
        if (tail != null) {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't add value to index "
                    + index + ", list's last index is " + (size - 1));
        }
        Node<T> currentINode = getNode(index);
        Node<T> newNode = new Node<>(currentINode.prev, value, currentINode);
        if (currentINode.prev == null) {
            head = newNode;
        } else {
            currentINode.prev.next = newNode;
        }
        currentINode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("Can't add list, it is null");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = getNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T prevValue = currentNode.value;
        currentNode.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        return removeNode(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == object || node.value.equals(object)) {
                removeNode(node);
                return true;
            }
            node = node.next;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Linked list (" + size + " elements): [");
        Node<T> node = head;
        while (node != null) {
            stringBuilder.append(node.value);
            if (node.next != null) {
                stringBuilder.append("; ");
            }
            node = node.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private boolean needToMoveFromHead(int index) {
        return index <= size / 2;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index "
                    + index + ", last index = " + (size - 1));
        }
        boolean moveFromHead = needToMoveFromHead(index);
        Node<T> currentNode = (moveFromHead ? head : tail);
        if (moveFromHead) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                if (currentNode == null) {
                    return null;
                }
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T removeNode(Node<T> node) {
        if (node == null) {
            return null;
        }
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return node.value;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
