package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_ERROR = "Index is wrong";
    private int listSize = 0;
    private Node<T> head;
    private Node<T> tail;

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

    private void makeConnections(Node<T> node) {
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        makeConnections(newNode);
        listSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index > listSize || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_ERROR);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            listSize++;
            return;
        }
        Node<T> currentNode = getNodeByIndex(index - 1);
        newNode.next = currentNode.next;
        currentNode.next = newNode;
        listSize++;
        if (newNode.next == null) {
            tail = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List is null");
        }
        for (T element : list) {
            if (element != null) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T removedValue = currentNode.value;
        unlink(currentNode);
        listSize--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (listSize == 0) {
            throw new IndexOutOfBoundsException(INDEX_ERROR);
        }
        Node<T> appropriateNode = head;
        while (appropriateNode != null) {
            if ((object == null && appropriateNode.value == null)
                    || (object != null && object.equals(appropriateNode.value))) {
                unlink(appropriateNode);
                listSize--;
                return true;
            }
            appropriateNode = appropriateNode.next;
        }
        return false;

    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }

    private void unlink(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (previousNode != null) {
            previousNode.next = nextNode;
        } else {
            head = node.next;
        }
        if (nextNode != null) {
            nextNode.prev = previousNode;
        } else {
            tail = node.prev;
        }
    }

    private void checkIndex(int index) {
        if (index >= listSize || index < 0 || listSize == 0) {
            throw new IndexOutOfBoundsException(INDEX_ERROR);
        }
    }
}
