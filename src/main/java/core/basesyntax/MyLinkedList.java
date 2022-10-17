package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newElement = new Node<>(null,value,null);
        if (size == 0) {
            head = newElement;
        } else {
            newElement.prev = tail;
            tail.next = newElement;
        }
        tail = newElement;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
            size++;
        } else {
            Node<T> newElement = getNodeByIndex(index);
            Node<T> newNode = new Node<>(newElement.prev, value, newElement);
            newElement.prev.next = newNode;
            newNode.next.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNodeByIndex(index);
        T oldValue = newNode.item;
        newNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNodeByValue(object);
        if (currentNode != null) {
            unlink(currentNode);
            return true;
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("It's wrong index for size: " + size);
        }
        Node<T> currentNode;
        if (index > (size / 2)) {
            currentNode = tail;
            for (int i = size - 1; i != index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i != index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private Node<T> getNodeByValue(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object || currentNode.item != null
                    && currentNode.item.equals(object)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void unlink(Node<T> removeItem) {
        if (removeItem.prev == null) {
            head = removeItem.next;
        } else {
            removeItem.prev.next = removeItem.next;
        }
        if (removeItem.next == null) {
            tail = removeItem.prev;
        } else {
            removeItem.next.prev = removeItem.prev;
        }
        size--;
    }
}
