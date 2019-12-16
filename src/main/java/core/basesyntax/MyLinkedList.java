package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int sizeCounter;

    public MyLinkedList() {
        head = null;
        tail = null;
        sizeCounter = 0;
    }

    public static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        public Node(T data, Node<T> next, Node<T> previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.next = null;
            newNode.previous = tail;
            tail = newNode;
        }
        sizeCounter++;
    }

    @Override
    public void add(T value, int index) {
        if (index == sizeCounter) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> tempNode = nodeOnPosition(index);
        Node<T> newNode = new Node<>(value, tempNode, tempNode.previous);
        tempNode.previous = newNode;
        sizeCounter++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listObj : list) {
            add(listObj);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodeOnPosition(index).data;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        nodeOnPosition(index).data = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return remove(nodeOnPosition(index).data);
    }

    @Override
    public T remove(T t) {
        for (Node<T> i = head; i != null; i = i.next) {
            if (t == i.data || i.data != null && i.data.equals(t)) {
                return unlinkNode(i);
            }
        }
        return null;
    }

    @Override
    public int size() {
        return sizeCounter;
    }

    @Override
    public boolean isEmpty() {
        return sizeCounter == 0;

    }

    private T unlinkNode(Node<T> unlinkedNode) {
        final T data = unlinkedNode.data;
        Node<T> next = unlinkedNode.next;
        Node<T> previous = unlinkedNode.previous;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            next = null;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            previous = null;
        }

        unlinkedNode.data = null;
        sizeCounter--;
        return data;
    }

    private Node<T> nodeOnPosition(int index) {
        checkIndex(index);
        Node<T> nodeOnPosition;
        if (sizeCounter / 2 > index) {
            nodeOnPosition = head;
            int counter = 0;
            while (counter != index) {
                nodeOnPosition = nodeOnPosition.next;
                counter++;
            }
        } else {
            nodeOnPosition = tail;
            int counter = sizeCounter - 1;
            while (counter != index) {
                nodeOnPosition = nodeOnPosition.previous;
                counter--;
            }
        }
        return nodeOnPosition;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= sizeCounter) {
            throw new IndexOutOfBoundsException();
        }
    }
}

