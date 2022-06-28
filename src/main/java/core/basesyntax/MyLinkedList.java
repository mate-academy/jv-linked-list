package core.basesyntax;

import java.util.List;


public class MyLinkedList<E> implements MyLinkedListInterface<E> {

    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    @Override
    public void add(E value) {

    }

    @Override
    public void add(E value, int index) {
    }

    @Override
    public void addAll(List<E> list) {
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(E value, int index) {
        return null;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public boolean remove(E object) {
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

    private static class Node<E>{
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(E item, Node<E> next, Node<E> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void unLink(Node<E> node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

}
