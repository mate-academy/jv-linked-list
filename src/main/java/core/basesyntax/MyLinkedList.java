package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            Node<T> newNode = new Node<>(value);
            newNode.next = head;
            head = newNode;
            size++;
            if (tail == null) {
                tail = head;
            }
        } else if (index >= size) {
                Node<T> newNode = new Node<>(value);
                if (tail == null) {
                    head = tail = newNode;
                } else {
                    tail.next = newNode;
                    tail = newNode;
                }
                size++;

            } else {
                Node<T> current = head;
                for (int i = 0; i < index; i++) {
                    current.next = current;
                }
                Node<T> temp = current.next;
                current.next = new Node<>(value);
                current.next.next = temp;
                size++;

            }
        }


    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size){
            return null;
        } else if (index == 0) {
            if(size == 0){
                return null;
            } else {
                T temp = head.value;
                head = head.next;
                size--;
                if (head == null) {
                    tail = null;
                }
            return temp;}
        } else if (index == size - 1) {
            if(size == 0){
                return null;
            } else if (size == 1) {
                T temp = head.value;
                head = tail= null;
                size = 0;
                return  temp;
            } else {Node<T> current = head;
            for (int i = 0; i < size-2; i++){
                current = current.next;
            }
            T temp = tail.value;
            tail = current;
            tail.next = null;
            size--;
            return temp;
            }
        } else {
            Node<T> previous = head;
            for(int i = 0; i < index; i++){
                previous = previous.next;
            }
            Node<T> current = previous.next;
            previous.next = current.next;
            size--;
            return current.next.value;
        }
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

    static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
