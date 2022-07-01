package core.basesyntax;

import java.util.List;


public class MyLinkedList<E> implements MyLinkedListInterface<E> {

    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    @Override
    public void add(E value) {
        if (size == 0){
            linkFirst(value);
        } else {
            Node<E> l = last;
            Node<E> newNode = new Node<>(l, value, null);
            last = newNode;
            l.next = newNode;
            size++;
        }

    }

    @Override
    public void add(E value, int index) {
        if (size == 0){
            linkFirst(value);
        } else {
            checkPositionIndex(index);
            if (index == size){
                add(value);
            }else {
                Node<E> x = node(index);
                x.prev = new Node<>( x.prev, value, x);
                size++;
            }
        }
    }

    @Override
    public void addAll(List<E> list) {
        for (E item : list) {
            add(item);
            size++;
        }
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;

    }

    @Override
    public E set(E value, int index) {
        checkElementIndex(index);
        Node<E> x = node(index);
        if (index == 0){

        }
        Node<E> newNode = new Node<>(x.prev, value, x.next);
        x.prev.next = newNode;
        x.next.prev = newNode;
        return null;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        Node<E> x = node(index);
        unLink(x);
        size--;
        return x.item;
    }

    @Override
    public boolean remove(E object) {
        if (object == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unLink(x);
                    size--;
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unLink(x);
                    size--;
                    return true;
                }
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

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void unLink(Node<E> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn`t exist");
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn`t exist");
        }
    }

    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void linkFirst(E value) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, value,f);
        first = newNode;
        if (f != null){
            f.prev = newNode;
        }else {
            last = newNode;
        }
        size++;
    }

}
