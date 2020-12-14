package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.next = next;
            this.value = element;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> temp = new Node<>(tail, value, null);
        if (head == null) {
            head = temp;
            tail = head;
        } else {
            tail.next = temp;
            tail = temp;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);
        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        } else {
            head = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> temp = getNodeByIndex(index);
        T oldValue = temp.value;
        temp.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> temp = getNodeByIndex(index);
        unlink(temp);
        return temp.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (temp.value == object || temp.value.equals(object)) {
                unlink(temp);
                return true;
            }
            temp = temp.next;
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

    private Node<T> getNodeByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("");
        }
        Node<T> temp;
        if (index <= size >> 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = 0; i < size - index - 1; i++) {
                temp = temp.prev;
            }
        }
        return temp;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }
}
