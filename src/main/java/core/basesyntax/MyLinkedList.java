package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    public static void main(String[] args) {
        MyLinkedList<Integer> ll = new MyLinkedList<>();
        ll.add(1);
        ll.add(11);
        System.out.println(ll.tail.prev);
        System.out.println(ll.tail.item);
        System.out.println(ll.tail.next);


    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> prev;
        Node<T> next = null;
        prev = size == 0 ? null : tail;
        Node newNode = new Node<>(prev, value, next);
        if (size == 0) {
            head = newNode;
        }
        if (size > 0) {
        tail.next = newNode;
        }
        tail = newNode;
        size++;
    }
    private static class Node<T> {
        T item;
        MyLinkedList.Node<T> next;
        MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value, int index) {
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
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
