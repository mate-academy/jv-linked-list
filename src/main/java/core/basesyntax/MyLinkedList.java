package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;
    private static class Node<T>{
        T item;
        Node<T> prev;
        Node<T> next;

        Node(Node<T> prev, T item, Node<T> next){
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    // Adds new value at the last position
    @Override
    public void add(T value) {
        if (last != null) {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }else{
            first = last = new Node<>(null, value, null);
        }
        size++;
    }

    // Adds new value at the index position or if index is out of size,
    // it throws IndexOutOfBoundsException
    @Override
    public void add(T value, int index) {
        if (index >=0 && index <= size){
            if (index == size) {
                add(value);
            }
            else if(index == 0){
                first = new Node<>(null, value, first);
            }else{
                Node<T> nextNode = getNode(index);
                Node<T> prevNode = nextNode.prev;

                prevNode.next = new Node<>(prevNode, value, nextNode);
                nextNode.prev = prevNode.next;

                size++;
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    // Extends LinkedList with list elements
    @Override
    public void addAll(List<T> list) {
        for (T element : list){
            add(element);
        }
    }

    // Returns an element value at the index position
    @Override
    public T get(int index) {
        Node<T> temporaryNode = first;
        int counter = 0;

        while(temporaryNode != null && counter != index){
            temporaryNode = temporaryNode.next;
            counter++;
        }
        if (temporaryNode == null) throw new IndexOutOfBoundsException();
        return temporaryNode.item;
    }

    // Returns an element at the index position
    public Node<T> getNode(int index) {
        Node<T> temporaryNode = first;
        int counter = 0;

        while(temporaryNode != null && counter != index){
            temporaryNode = temporaryNode.next;
            counter++;
        }
        if (temporaryNode == null) throw new IndexOutOfBoundsException();
        return temporaryNode;
    }

    // Sets a new value to an existing element and
    // returns the value of the element
    @Override
    public T set(T value, int index) {
        Node<T> temporaryNode = first;
        int counter = 0;

        while(temporaryNode != null && counter != index){
            temporaryNode = temporaryNode.next;
            counter++;
        }
        if (temporaryNode == null) throw new IndexOutOfBoundsException();

        T prevValue = temporaryNode.item;
        temporaryNode.item = value;

        return prevValue;
    }

    // Removes an element at the index position and returns this element
    @Override
    public T remove(int index) {
        Node<T> removalNode = getNode(index);
        T removalValue = removalNode.item;

        if (index == 0) {
            first = first.next;
            if (first != null) first.prev = null;
            else last = null;
        }else if(index == size-1){
            last.prev.next = null;
            last = last.prev;
        }
        else {
            removalNode.prev.next = removalNode.next;
            removalNode.next.prev = removalNode.prev;
        }

        size--;
        return removalValue;
    }

    // Removes an element with item equals object and
    // returns true if the element is deleted or false if isn't
    @Override
    public boolean remove(T object) {
        Node<T> temporaryNode = first;
        Node<T> removalNode = null;
        int counter = 0;

        while(temporaryNode != null){
            if (temporaryNode.item == null){
                if (object == null) {
                    removalNode = temporaryNode;
                    break;
                }
            }
            else if(temporaryNode.item.equals(object)) {
                removalNode = temporaryNode;
                break;
            }
            temporaryNode = temporaryNode.next;
            counter++;
        }
        if (removalNode == null) return false;
        else{
            remove(counter);
            return true;
        }
    }

    // Returns actual size of LinkedList
    @Override
    public int size() {
        return size;
    }

    // Returns true if LinkedList is empty and false if it isn't
    @Override
    public boolean isEmpty() {
        if (first == null) return true;
        return false;
    }
}
