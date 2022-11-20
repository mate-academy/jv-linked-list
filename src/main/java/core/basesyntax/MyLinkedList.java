package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> tailNode = tail;
        Node<T> newNode = new Node<>(tailNode, value, null);
        tail = newNode;
        if (tailNode == null) {
            head = newNode;
        } else {
            tailNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        index(index);
        Node<T> newlingNode = getIndex(index);
        Node<T> newNode = new Node<T>(newlingNode.prev, value, newlingNode);
        if (newlingNode.prev == null) {
            head = newNode;
        } else {
            newlingNode.prev.next = newNode;
        }
        newlingNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T numbers : list) {
            add(numbers);
        }
    }

    @Override
    public T get(int index) {
        return getIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        index(index);
        Node<T> setNode = getIndex(index);
        T setResult = setNode.item;
        setNode.item = value;
        return setResult;
    }

    @Override
    public T remove(int index) {
        index(index);
        Node<T> removeNode = getIndex(index);
        T removeValue = removeNode.item;
        getremoveNode(removeNode);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> thisNode = head;
        while (thisNode != null) {
            if (thisNode.item == object || object != null && object.equals(thisNode.item)) {
                getremoveNode(thisNode);
                return true;
            }
            thisNode = thisNode.next;
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

    private void index(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Something was wrong.");
        }
    }

    private Node<T> getIndex(int index) {
        index(index);
        Node<T> newlingNode;
        if (index < (size << 1)) {
            newlingNode = head;
            for (int i = 0; i < index; i++) {
                newlingNode = newlingNode.next;
            }
        } else {
            newlingNode = tail;
            for (int i = 0; i < index; i--) {
                newlingNode = newlingNode.prev;
            }
        }
        return newlingNode;
    }

    private void getremoveNode(Node<T> removeNode) {
        if (removeNode == tail) {
            tail = removeNode.prev;
        } else if (removeNode == head) {
            head = removeNode.next;
        } else {
            removeNode.next.prev = removeNode.prev;
            removeNode.prev.next = removeNode.next;
        }
        size--;
    }
}
