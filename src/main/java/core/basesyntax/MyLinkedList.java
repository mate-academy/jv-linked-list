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
            head = head.prev;
            size++;
        } else {
            Node<T> curentNode = head;
            for (int i = 0; i < index; i++) {
                curentNode = curentNode.next;
            }
            curentNode.prev = new Node<>(curentNode.prev, value, curentNode);
            curentNode.prev.prev.next = curentNode.prev;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
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
        T result = curentNode.value;
        curentNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedItem = null;
        Node<T> curentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                if (index == 0) {
                    removedItem = head.value;
                    if (size == 1) {
                        head = null;
                        size--;
                        return removedItem;
                    }
                    head = head.next;
                    head.prev = null;
                    size--;
                }  else if (index == size -1){
                    removedItem = tail.value;
                    tail.prev.next = null;
                    tail = tail.prev;
                    size--;
                } else {
                    removedItem = curentNode.value;
                    curentNode.prev.next = curentNode.next;
                    curentNode.next.prev = curentNode.prev;
                    size--;
                }
            }
            curentNode = curentNode.next;
        }
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> curentNode = head;
        for (int i = 0; i < size; i++) {
            if (curentNode.value == null && object == null) {
                remove(i);
                return true;
            } else if (curentNode.value.equals(object)){
                remove(i);
                return true;
            }
            curentNode = curentNode.next;
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
