package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int count;
    private Node<T> newNode = new Node<>(null, null, null);
    private Node<T> heard;
    private Node<T> tail;

    @Override
    public void add(T value) {
        newNode = new Node<>(tail, value, null);
        if (count == 0) {
            heard = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        count++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size()) {
            add(value);
            return;
        }
        if (index == 0) {
            heard = new Node<>(null,value,finedNodeByIndex(index));
            finedNodeByIndex(index).prev = heard;
            count++;
            return;
        }
        if (count <= index || index < 0) {
            throw new IndexOutOfBoundsException("Out of bound exception for index: " + index);
        }
        Node<T> nextNode = finedNodeByIndex(index);
        newNode = new Node<>(nextNode.prev, value, nextNode);
        nextNode.prev.next = newNode;
        nextNode.prev = newNode;
        count++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        outOfIndexException(index);
        return finedNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        outOfIndexException(index);
        T oldValue = finedNodeByIndex(index).item;
        finedNodeByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        outOfIndexException(index);
        final T value = finedNodeByIndex(index).item;
        Node<T> removedNode = finedNodeByIndex(index);
        Node<T> prev = removedNode.prev;
        Node<T> next = removedNode.next;
        if (prev == null) {
            heard = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        count--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> value = heard;
        for (int i = 0; i < size(); i++) {
            if (value.item == object || value.item.equals(object)) {
                remove(i);
                return true;
            }
            value = value.next;
        }
        return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void outOfIndexException(int index) {
        if (count <= index || index < 0) {
            throw new IndexOutOfBoundsException("Out of bound exception for index: " + index);
        }
    }

    public Node<T> finedNodeByIndex(int index) {
        Node<T> needfulNode;
        if (size() / 2 >= index) {
            needfulNode = heard;
            for (int i = 0; i < index; i++) {
                needfulNode = needfulNode.next;
            }
        } else {
            needfulNode = tail;
            for (int i = size() - 1; i > index; i--) {
                needfulNode = needfulNode.prev;
            }
        }
        return needfulNode;
    }

}
