package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value) {
            this.prev = prev;
            this.value = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        //tail.next = head;
        //head.prev = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index can`t be:" + index
                    + " for LinkedList size:" + size);
        }



        Node<T> tmp = head;
        for (int i = 0; i < index; i++) { // use head/tail
            tmp = tmp.next;
        }

        if (tmp == null) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(tmp.prev, value, tmp);
        if (tmp == head) {
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        //if ()
        tmp.prev.next = newNode;
        tmp.prev = newNode;
        size++;
    }

    //private void addFromTail() {
    //
    //}

    //private void addFromHead() {
    //
    //}

    @Override
    public void addAll(List<T> list) {
        for (Object element : list) {
            add((T) element);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index can`t be:" + index
                    + " for LinkedList size:" + size);
        }
        Node tmp = head;
        for (int i = 0; i < index; i++) {// use head/tail
            tmp = tmp.next;
        }


        return (T) tmp.value;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index can`t be:" + index
                    + " for LinkedList size:" + size);
        }
        Node tmp = head;
        for (int i = 0; i < index; i++) { // use head/tail
            tmp = tmp.next;
        }


        final Object previousValue = tmp.value;
        tmp.value = value;
        return (T) previousValue;
    }

    @Override
    public T remove(int index) {
        checkOutOfMemory(index);
        Node tmp = head;
        for (int i = 0; i < index; i++) { // use head/tail
            tmp = tmp.next;
        }
        final Object removedValue = tmp.value;
        unlink(tmp);
        return (T) removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node tmp = head;
        while (tmp != null && !(tmp.value == object || (tmp.value != null && tmp.value.equals(object)))) {
            tmp = tmp.next;
        }
        if (tmp == null) {
            return false;
        }
        unlink(tmp);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlink(Node<T> tmp) {
        if (tmp != tail && tmp != head) {
            tmp.prev.next = tmp.next;
            tmp.next.prev = tmp.prev;
        }
        if(tmp == head) {
            head = tmp.next;
        }
        if (tmp == tail) {
            tail = tmp.prev;
        }
        size--;
    }

    private void checkOutOfMemory(int index) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index can`t be:" + index
                    + " for LinkedList size:" + size);
        }
    }
}
