package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        indexCheckForAdding(index);
        if (index == size){
            add(value);
        } else {
            Node<T> currentNode = node(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (currentNode.prev == null) {
                head = newNode;
            } else {
                currentNode.prev.next = newNode;
            }
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return node(index).element;
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
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        return "MyLinkedList{" +
                "size=" + size + "," + System.lineSeparator() +
                "head=" + head + "," + System.lineSeparator() +
                "tail=" + tail + " }";
    }

    private void indexCheck(int index) {
        if (index >= 0 && index < size) {
            return;
        }
        throw new IndexOutOfBoundsException("Wrong index. Index should be: 0 <= index <= "
                + (size - 1));
    }

    private void indexCheckForAdding(int index) {
        if (index >= 0 && index <= size) {
            return;
        }
        throw new IndexOutOfBoundsException("Wrong index. Index should be: 0 <= index <= "
                + size);
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> node = head;
            for (int i = 0; i < index; i++)
                node = node.next;
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--)
                node = node.prev;
            return node;
        }
    }

}

class Node<T>{
    T element;
    Node<T> prev;
    Node<T> next;

    public Node(Node<T> prev, T element, Node<T> next) {
        this.prev = prev;
        this.element = element;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "element=" + element +
                "}";
    }
}
