package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public E getItem() {
            return item;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

    }

    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(null,value,null);
            tail = head;
        } else if (size > 0) {
            linkNodes(tail,new Node<T>(tail,value,null));
        } else {
            throw new IndexOutOfBoundsException("Failed error");
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
        Node<T> newNode = new Node<T>(node.prev,value,node);
        linkNodes(node.prev,newNode);
        linkNodes(newNode,node);
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
        Node<T> n = getNode(index);
        return n.item;

    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldvalue = node.item;
        node.item = value;
        return oldvalue;

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
        Node<T> currNode = head;
        do {
            if (Objects.equals(currNode.item,object)) {
                linkNodes(currNode.prev,currNode.next);
                size--;
                return true;
            }
            currNode = currNode.next;
        } while (currNode.next != null);

        return false;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public boolean isEmpty() {

        return size <= 0;
    }

    private Node<T> getNode(int index) {
        if (head == null || index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        Node<T> currNode;
        if (size / 2 >= index) {
            currNode = head;
            for (int i = 1;i <= index;i++) {
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
