package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        public Node(Node<T> prev,T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null,value,null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            newNode = new Node<>(tail,value,null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index,size + 1);
        Node<T> newNode = new Node<>(null,value,null);
        Node<T> node = head;
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode = new Node<>(null,value,head);
            newNode.next = head;
            head = newNode;
        } else {
            for (int i = 0; i < index - 1; i++) {
                node = node.next;
            }
            newNode.next = node.next;
            newNode.prev = node;
            node.next.prev = newNode;
            node.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T elements:list) {
            add(elements);
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index,size);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.element;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index,size);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        T deletednode = node.element;
        node.element = value;
        return deletednode;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.element != null && node.element.equals(object) || node.element == object) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private T unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
        } else if (node == tail) {
            tail = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node.element;
    }
}
