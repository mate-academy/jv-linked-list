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
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
            size++;

        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        if (index  == size) {
            add(value);
        } else if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head.next.prev = head;
            size++;
        } else {
            Node<T> curentNode = head;
            for (int i = 0; i < index; i++) {
                curentNode = curentNode.next;
            }
            curentNode.next = new Node<>(curentNode, value, curentNode.next);
            curentNode.next.next.prev = curentNode.next;
            size++;
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
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            head.next.prev = null;
            head = head.next;
            size--;
        } else if (index == size - 1){
            tail.prev.next = null;
            tail = tail.prev;
            size--;
        } else {
            Node<T> curentNode = head;
            for (int i = 0; i < size; i++) {
                curentNode = curentNode.next;
                if (i == index) {
                    curentNode.prev.next = curentNode.next;
                    curentNode.next.prev = curentNode.prev;
                    size--;
                }
            }
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
