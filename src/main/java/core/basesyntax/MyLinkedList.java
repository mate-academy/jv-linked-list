package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

    }

    @Override
    public boolean add(T value) {
        Node<T> lastElem = tail;
        Node<T> newNode = new Node(lastElem,value,null);

        tail = newNode;
        if (lastElem == null) {
            head = newNode;
        } else {
            lastElem.next = tail;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            Node<T> currentElem = getNodeByIndex(index);
            Node<T> newNode = new Node<T>(currentElem.prev, value, currentElem);
            currentElem.prev.next = newNode;
            currentElem.prev = newNode;
            size++;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        isIndexOPresent(index);
        int actualIndex = 0;
        Node<T> current = head;
        while (actualIndex < index) {
            current = current.next;
            actualIndex++;
        }
        return current;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentElem = getNodeByIndex(index);
        T oldNodeData = currentElem.data;
        currentElem.data = value;
        return oldNodeData;
    }

    @Override
    public T remove(int index) {
        Node<T> currentElem = getNodeByIndex(index);
        if (currentElem.prev == null) {
            head = currentElem.next;
        } else {
            currentElem.prev.next = currentElem.next;
        }
        if (currentElem.next == null) {
            tail = currentElem.prev;
        } else {
            currentElem.next.prev = currentElem.prev;
        }
        currentElem.next = null;
        currentElem.prev = null;
        size--;
        return currentElem.data;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentElem = head;
        for (int i = 0; i < size; i++) {
            if (t == currentElem.data
                    || currentElem.data != null
                    && currentElem.data.equals(t)) {
                remove(i);
                return true;
            }
            currentElem = currentElem.next;
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

    private void isIndexOPresent(int index) {
        if (index < 0 || index >= size && index != 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
