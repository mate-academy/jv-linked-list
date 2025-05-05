package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> head;  
    private Node<T> tail;  
    private int size;    

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }


    @Override
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> elements) {
        for (T element : elements) {
            add(element); 
        }
    }

    @Override
    public void add(T item, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (index == size) {
            add(item);  // додаємо до кінця
            return;
        }

        Node<T> newNode = new Node<>(item);
        if (index ==
