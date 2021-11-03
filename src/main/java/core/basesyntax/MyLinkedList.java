package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        Node(Node<T> prev,T item, Node<T> next){
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
    private Node<T> tail;
    private Node<T> head;
    private int size;

    public MyLinkedList(){
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (tail != null) {
            tail.next = node;
        }
        tail = node;
        if(head == null) {
            head = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = getNodefromIndex(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.next.prev = newNode;
        }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return getItemFromIndex(index);
    }

    @Override
    public T set(T value, int index) {
        chekForTrueIndex(index);
        return setItemToNode(index, value);
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodefromIndex(index);
        if (isTail(node)) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
        }
        if (isHead(node)) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size - 1; i++) {
            if (node.item == object|| node.item != null && node.item.equals(object)) {
                remove(i);
                return true;
            }
            if (node.next != null) {
                node = node.next;
            } else {
                return false;
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
        return size == 0;
    }

    private void createNewNode(T item) {
        Node<T> node = new Node<T>(tail, item, null);
        if (tail != null) {
            tail.next = node;
        }
        tail = node;
        if(head == null) {
            head = node;
        }
        size++;
    }

    private void chekForTrueIndex(int index) {
        if (index < 0 && index > size()-1) {
            throw new RuntimeException("Index not found");
        }
    }

    private Node<T> getNodefromIndex(int index) {
        Node<T> nodeFromIndex = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return nodeFromIndex;
            }
            nodeFromIndex = head.next;
        }
        return null;
    }

    private T getItemFromIndex(int index) {
            return getNodefromIndex(index).item;
    }

    private T setItemToNode(int index, T value) {
        Node<T> node;
        for (int i = 0; i <= index; i++) {
            node = head.next;
            if (i == index) {
                node.item = value;
            }
        }
        return null;
    }

    private boolean isTail(Node<T> node) {
        return node == tail;
    }

    private boolean isHead(Node<T> node) {
        return node == head;
    }
}
