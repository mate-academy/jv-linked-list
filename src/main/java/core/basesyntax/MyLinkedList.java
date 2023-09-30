package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String indexError = "Index is wrong";
    private int listSize = 0;
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
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
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        listSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index > listSize || index < 0) {
            throw new IndexOutOfBoundsException(indexError);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            listSize++;
            return;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.next;
        }
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
            throw new NullPointerException("Input is null");
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                add(list.get(i));
            }
        }
    }

    @Override
    public T get(int index) {
        if (index >= listSize || index < 0 || listSize == 0) {
            throw new IndexOutOfBoundsException(indexError);
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        if (index >= listSize || index < 0 || listSize == 0) {
            throw new IndexOutOfBoundsException(indexError);
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= listSize || index < 0 || listSize == 0) {
            throw new IndexOutOfBoundsException(indexError);
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T removedValue = currentNode.value;
        unlink(currentNode);
        listSize--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (listSize == 0) {
            throw new IndexOutOfBoundsException(indexError);
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
}
