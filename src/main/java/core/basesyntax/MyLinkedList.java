package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

class Train {
    public static void main(String[] args) {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();

        myLinkedList.add(229,0);
        myLinkedList.add(255,1);

            System.out.println(myLinkedList.get(0));
    }
}

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firsNode;
    private Node<T> lastNode;

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
//        if (index >= 0 && index <= size)
//            throw new IndexOutOfBoundsException();
        if (index == size)
            linkLast(value);
        else
            linkBefore(value, node(index));
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
//        if (index > 0 && index < size)
//            throw new IndexOutOfBoundsException();
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
//        int currentIndex = 0;
//        Node<T> tempFirst = firsNode;
//        while (tempFirst != null) {
//            if (currentIndex == index) {
//                return tempFirst.getElement();
//            } else {
//                tempFirst = tempFirst.getNext();
//                currentIndex++;
//            }
//        }
//        throw new IllegalArgumentException();


        return node(index).element;
//        T oldVal = x.getElement();
//        x.setElement() = value;
//        return oldVal;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T t) {
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

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;

        }
    }


        public void linkLast(T value) {
            final Node<T> tempLast = lastNode;
            final Node<T> newNode = new Node<T>(tempLast, value, null);
            lastNode = newNode;
            if (tempLast == null) {
                firsNode = newNode;
            } else {
                tempLast.next = newNode;
            }
            size++;
        }

        public void linkBefore(T value, Node<T> succ) {
            final Node<T> pred = succ.prev;
            final Node<T> newNode = new Node<>(pred, value, succ);
            succ.prev = newNode;
            if (pred == null)
                firsNode = newNode;
            else
                pred.next = newNode;
            size++;
        }

            Node<T> node ( int index){
                if (index < (size >> 1)) {
                   Node<T> x = firsNode;
                    for (int i = 0; i < index; i++)
                        x = x.next;
                    return x;
                } else {
                    Node<T> x = lastNode;
                    for (int i = size - 1; i > index; i--)
                        x = x.prev;
                    return x;
                }
            }
}



