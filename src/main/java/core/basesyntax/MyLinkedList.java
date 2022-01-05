package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(null,value,null);
            tail = head;
        } else {
            linkNodes(tail,new Node<T>(tail,value,null));
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<T>(node.prev, value, node);
        linkNodes(node.prev, newNode);
        linkNodes(newNode, node);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T valueBefore = node.item;
        node.item = value;
        return valueBefore;

    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        linkNodes(node.prev,node.next);
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> rmNode = head;
        do {
            if ((rmNode.item == object) || rmNode.item.equals(object)) {
                linkNodes(rmNode.prev,rmNode.next);
                size--;
                return true;
            }
            rmNode = rmNode.next;
        } while (rmNode.next != null);
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

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        Node<T> currNode;
        if (size / 2 >= index) {
            currNode = head;
            for (int i = 1; i <= index; i++) {
                currNode = currNode.next;
            }
        } else {
            currNode = tail;
            for (int i = 1;i < (size - index);i++) {
                currNode = currNode.prev;
            }
        }
        return currNode;
    }

    private void linkNodes(Node<T> first,Node<T> second) {
        if (first != null) {
            first.next = second;
            if (first.prev == null) {
                head = first;
            }
        } else {
            head = second;
        }
        if (second != null) {
            second.prev = first;
            if (second.next == null) {
                tail = second;
            }
        } else {
            tail = first;
        }
    }
}
