package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value,null);
            tail = head;
            size++;
            System.out.println(size);
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
            size++;
            System.out.println(size);

        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size){
            System.out.println("index");
            throw new IndexOutOfBoundsException();
        }
        if (index  == size) {
            add(value);
            System.out.println(size);
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
            size++;
            System.out.println(size);
        } else {
            Node<T> curentNode = head;
            for (int i = 0; i < index; i++) {
                curentNode = curentNode.next;
            }
            curentNode.prev = new Node<>(curentNode.prev, value, curentNode);
            curentNode.prev.prev.next = curentNode.prev;
            size++;
            System.out.println(size);
        }
    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> curentNode = head;
        for (int i = 0; i < index; i++) {
            curentNode = curentNode.next;
        }
        System.out.println(curentNode.value);
        return curentNode.value;
        }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> curentNode = head;
        for (int i = 0; i < index; i++) {
            curentNode = curentNode.next;
        }
        curentNode.value = value;
        System.out.println(curentNode.value + " " +  index);
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (size == 1) {
            head = null;
            size--;
            System.out.println(size);
        } else if (index == size){
            tail.prev.next = null;
            tail = tail.prev;
            size--;
            System.out.println(size);

        } else {
            Node<T> curentNode = head;
            for (int i = 0; i < index; i++) {
                curentNode = curentNode.next;
            }
            curentNode.prev.next = curentNode.next;
            curentNode.next.prev = curentNode.prev;
            size--;
            System.out.println(size);
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> curentNode = head;
        for (int i = 0; i < size; i++) {
            curentNode = curentNode.next;
            if (curentNode.value.equals(object)){
                return true;
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

    private void checkIndex(int index) {
        if (index >= size || index <0){
            throw new IndexOutOfBoundsException();
        }
    }
}
