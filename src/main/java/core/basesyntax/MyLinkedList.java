package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int EMPTY = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value, null, null);
        if (head == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
//        if (!validBound(index)) {
//            throw new IndexOutOfBoundsException("Index out of bound");
//        } else {
//            if (index == size) {
//                add(value);
//            } else {
//                Node<T> currentNode = getCurrentNode(index);
//                Node<T> node = new Node<>(value, currentNode, currentNode.prev);
//                currentNode.prev.next = node;
//                currentNode.prev = node;
//                size++;
//            }
//        }

        if (index == 0) {
            Node<T> node = new Node<>(value, head, null);
            head = node;
            if (size == 0) {
                tail = node;
            }
            size++;
        } else if (index == size) {
            add(value);
        } else if (validBound(index)) {
            Node<T> currentNode = getCurrentNode(index);
            Node<T> node = new Node<>(value, currentNode, currentNode.prev);
            currentNode.prev.next = node;
            currentNode.prev = node;
            size++;
        } else {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    @Override
    public void addAll(List<T> list) {
        T[] array = (T[]) list.toArray();
        for (T element : array) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (validBound(index)) {
            return (T) getCurrentNode(index).item;
        } else {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    @Override
    public T set(T value, int index) {
        if (validBound(index)) {
            Node<T> currentNode = getCurrentNode(index);
            T result = currentNode.item;
            currentNode.item = value;
            return result;
        } else {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    @Override
    public T remove(int index) {
        if (!validBound(index)) {
            throw new IndexOutOfBoundsException("Index out of bound");
        } else if (index == 0) {
            T result = head.item;
            head = head.next == null ? null : head.next;
            size--;
            return result;
        } else if (index == size - 1) {
            tail = tail.prev;
            size--;
            return tail.next.item;
        } else {
            Node<T> currentNode = getCurrentNode(index);
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            size--;
            return currentNode.item;
        }
    }

    @Override
    public boolean remove(T object) {
        int index = -1;
        Node<T> currentNode = head;
        for (int  i = 0; i < size; i++) {
            if (currentNode.item == object
                    || currentNode.item != null && currentNode.item.equals(object)) {
                index = i;
                break;
            }
            currentNode = currentNode.next;
        }
        if (index != -1) {
            remove(index);
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
        return size == EMPTY;
    }

    private boolean validBound(int index) {
        return (index >= 0 && index < size);
    }

    private Node getCurrentNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
