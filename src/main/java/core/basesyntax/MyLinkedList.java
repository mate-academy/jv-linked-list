package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> headNode;
    private Node<T> lastNode;

    /*+ (1 2 3 LL)
       1)   add(e, 0)   e 1 2 3
       Node temp = new Node(value, lastNode, null)
       e.next = headNode;
       headNode.prev = e;
       headNode = e;
       size++;

       (1 2 3 LL)
       2) add(e,2)   1 2 e 3
       Node temp = new Node(value, null, null)
       NodeFor = getNode(index); мы на 3 элементе
       NodeE.prev = NodeFor.prev;
       NodeE.next = nodeFor;
       NodeFor.prev = NodeE;
       NodeE.prev.next = NodeE;
       size++;

        (1 2 3 LL)
       3) if(index == size)
       add(e, 3)  1 2 3 e
       TempNode = new Node(E, null, null);
        tempNode.prev = lastNode;
       lastNode.next = tempNode;
       lastNode = temp;
        size++;





           */
    @Override
    public boolean add(T value) {
        Node<T> temp = new Node<>(value, lastNode, null);
        if (size == 0) {
            headNode = temp;
        } else {
            lastNode.next = temp;
        }
        lastNode = temp;
        size++;
        return true;
    }/*
    1)   add(e, 0)   e 1 2 3
       Node temp = new Node(value, lastNode, null)
       e.next = headNode;
       headNode.prev = e;
       headNode = e;
       size++;
       */

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> temp = new Node<>(value, null, null);
        if (index == 0) {

        } else if (index == size) {
            //3st
        } else {
            //2st
        }


    }

    @Override
    public boolean addAll(List<T> list) {
        return false;
    }

    @Override
    public T get(int index) {
        Node<T> temp = headNode;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
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

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            //TODO
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> temp = headNode;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private static class Node<T> {
        T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T element, Node<T> prev, Node<T> next) {
            this.data = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
/*
    Node
    prev = null
    data = null
    next = null

 */