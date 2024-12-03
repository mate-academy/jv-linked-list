package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    static class Node<T>{
        T item;
        Node<T> prev;
        Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }
    private Node<T> head;
    private Node<T> tail;
    private int size;
    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0){
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<T> newNode = new Node<>(value);
        if (head == null){
            head = tail = newNode;
        } else if (index == 0){
            newNode.next = head;
            head = newNode;
        } else if (index == size){
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = gettNodeByIndex(index);
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    private Node<T> gettNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            list.add(current.item);
            current = current.next;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size){
            throw new ArrayIndexOutOfBoundsException();
        }
        return gettNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = gettNodeByIndex(index);
        current.item = value;
        return current.item;

    }

    @Override
    public T remove(int index) {
        T object = null;
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (index == 0){
            object = head.item;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else if (index == size - 1){
            object = tail.item;
            tail = tail.prev;
        } else {
            Node<T> previous = gettNodeByIndex(index - 1);
            object = previous.item;
            previous.next = previous.next.next;
        }
        size--;
        return object;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.item == object && current.prev == null) {
                current.next = head;
                current.next.prev = null;
                return true;
            } else if (current.item == object){
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
    public void checkIndex(int index){
        if (index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
