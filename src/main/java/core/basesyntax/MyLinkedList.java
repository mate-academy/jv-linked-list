package core.basesyntax;

import java.util.ArrayList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private List<Node<T>> nodeList;
    private int size;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public static void unlink(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public MyLinkedList() {
        this.head = new Node<T>(null, null, null);
        this.tail = new Node<T>(null, null, null);
        head.next = tail;
        tail.prev = head;
        this.nodeList = new ArrayList<Node<T>>();
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(tail.prev, value, tail);
        tail.prev.next = newNode;
        tail.prev = newNode;
        nodeList.add(newNode);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> newNode = new Node<T>(null, value, null);
        if (index == 0) {
            newNode = new Node<>(head, value, head.next);
            head.next.prev = newNode;
            head.next = newNode;
            nodeList.add(0, newNode);
        } else if (index == size) {
            newNode = new Node<>(tail.prev, value, tail);
            tail.prev.next = newNode;
            tail.prev = newNode;
            nodeList.add(newNode);
        } else {
            newNode = new Node<>(nodeList.get(index),
                    value,
                    nodeList.get(index).next);
            nodeList.get(index).next.prev = newNode;
            nodeList.get(index).next = newNode;
            nodeList.add(index, newNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodeList.get(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = nodeList.get(index).item;
        nodeList.get(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node.unlink(nodeList.get(index));
        T removedItem = nodeList.get(index).item;
        nodeList.remove(index);
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((nodeList.get(i).item == object)
                    || (nodeList.get(i).item != null
                    && nodeList.get(i).item.equals(object))) {
                Node.unlink(nodeList.get(i));
                nodeList.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
