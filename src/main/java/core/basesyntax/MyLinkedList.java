package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
        first = new <T> Node<T>(null, null, null);
        last = new <T> Node<T>(null, null, null);
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }


    @Override
    public boolean add(T value) {
        if (size == 0) {
            first.value = value;
            last.value = value;
            first.next = last;
            last.previous = first;
            size++;
            return true;
        }

        Node<T> newNode = last;
        newNode.value = value;
        last = new <T>Node<T>(value, newNode, null);
        last.value = value;
        newNode.next = last;
        size++;
        return true;
    }


    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Not in range");
        }
//        if (index == 0) {
//            Node<T> newNode = first;
//            first.value = value;
//            newNode.previous = first;
//            first.next = newNode;
//            return;
//        }

        Node<T> tempNode = new <T> Node<T>(value,null, null);
        if (index == 0){
           tempNode.value = value;
           first.next = tempNode;
           tempNode.previous = first;
           tempNode.next.previous = tempNode;

        }

        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        Node<T> newNode = tempNode.previous;
        newNode.value = value;
        newNode.next = tempNode;
        tempNode.previous = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> tempNode = first;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        return tempNode.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Not in range");
        }
        if (index == 0) {
            first.value = value;
            return value;
        }
        Node<T> tempNode = first;
        for (int i = 0; i <= index; i++) {
            tempNode = tempNode.next;
        }
        tempNode.previous.value = value;
        return value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> tempNode = first;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.next;
        }
        T result = tempNode.value;
        tempNode.previous.next = tempNode.next;
        tempNode.next.previous = tempNode.previous;
        size--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        Node<T> tempNode = first;
        for (int i = 0; i <= size; i++) {
            if (tempNode.value == t) {
                tempNode.previous.next = tempNode.next;
                tempNode.next.previous = tempNode.previous;
                return true;
            }
            tempNode = tempNode.next;

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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not in range of the list!");
        }
    }
}
