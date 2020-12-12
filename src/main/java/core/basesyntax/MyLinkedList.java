package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        add(value, size);
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (size == 0 && index == 0) {
            newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else if (index == size) {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            isValidIndex(index);
            Node<T> nodeAtIndex = getNodeByIndex(index);
            if (nodeAtIndex == head) {
                newNode = new Node<>(null, value, nodeAtIndex);
                nodeAtIndex.prev = newNode;
                head = newNode;
            } else {
                newNode = new Node<>(nodeAtIndex.prev, value, nodeAtIndex);
                nodeAtIndex.prev.next = newNode;
                nodeAtIndex.prev = newNode;
            }
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        Node<T> nodeAtIndex = getNodeByIndex(index);
        return nodeAtIndex.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeAtIndex = getNodeByIndex(index);
        T oldValue = nodeAtIndex.value;
        nodeAtIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeAtIndex = getNodeByIndex(index);
        unlinkNode(nodeAtIndex);
        return nodeAtIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> iterationNode = head;
        while (iterationNode != null) {
            if ((object == null && iterationNode.value == null)
                    || (iterationNode.value != null && iterationNode.value.equals(object))) {
                unlinkNode(iterationNode);
                return true;
            }
            iterationNode = iterationNode.next;
        }
        return false;
    }

    private Node<T> getNodeByIndex(int index) {
        isValidIndex(index);
        Node<T> iterationNode;
        if (index < size / 2) {
            iterationNode = head;
            for (int i = 0; i < index; i++) {
                iterationNode = iterationNode.next;
            }
        } else {
            iterationNode = tail;
            for (int i = size - 1; i > index; i--) {
                iterationNode = iterationNode.prev;
            }
        }
        return iterationNode;
    }

    private void unlinkNode(Node<T> node) {
        if (node == tail) {
            tail.next = null;
            tail = node.prev;
        } else if (node == head) {
            head = node.next;
            head.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void isValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index for LinkedList with size " + size);
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
        Node<T> prev;
        T value;
        Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
