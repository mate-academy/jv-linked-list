package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        }else{
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if(size == 0){
            Node<T> node = new Node<>(tail, value, null);
            head = node;
            tail = node;
        }
        linkBefore(value, nodeForIndex(index));
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> currentNode = tail;
        Object[] objects = list.toArray();
        ;


    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return nodeForIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> firstNode = nodeForIndex(index);
        Node<T> valueNode = new Node<>(firstNode.prev, value, firstNode.next);
        firstNode = null;

        return valueNode.value;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> removedNode = nodeForIndex(index);
        size--;
        return unlink(removedNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> result = head;
        for(int i = 0; i < size;i++){
            if(result.value.equals(object)){
                unlink(result);
                return true;
            }
            result = result.next;
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

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void linkBefore(T value, Node<T> nodeForIndex) {
        Node<T> prevNode = nodeForIndex.prev;
        Node<T> currentNode = new Node<>(prevNode, value, nodeForIndex);
        nodeForIndex.prev = currentNode;
        if (prevNode == null) {
            head = currentNode;
        }else {
            prevNode.next = currentNode;
        }
        size++;
    }

    private Node<T> nodeForIndex(int index) {

        Node<T> newNode = head;
        for(int i = 0; i < index; i++){
            newNode = newNode.next;
        }
        return newNode;
    }

    private T unlink(Node<T> node){
        Node<T> firstNode = node.prev;
        Node<T> secondNode = node.next;
        node = new Node<>(null, node.value, null);
        firstNode.next = secondNode;
        secondNode.prev = firstNode;
        return node.value;
    }
}


