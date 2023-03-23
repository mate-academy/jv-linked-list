package core.basesyntax;

import java.util.LinkedList;
import java.util.List;


public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> head;
    Node<T> tale;

    @Override
    public void add(T value) {
        new Node(tale, value, null); // todo
    }

    @Override
    public void add(T value, int index) {
        int indexCounter = 0;
        if (index == 0) {
            Node<T> initialHeadNode = head;
            head = new Node<>(null, value, initialHeadNode);
            initialHeadNode.prev = head;
            return;
        }
        Node<T> tempNode = head.next;
        while (indexCounter != index) {
            indexCounter++;
            if (indexCounter == index) {
                Node<T> toAdd = new Node<>(tempNode.prev, value, tempNode);
                tempNode.prev.next = toAdd;
                tempNode.prev = toAdd;
            }
            tempNode = tempNode.next;

        }
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private static class Node<T>{
        Node<T> prev;
        Node<T> next;
        T body;

        public Node(Node<T> prev, T body, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.body = body;
        }
    }
}
