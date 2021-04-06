package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Index out of bounds";
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {

    }

    @Override
    public boolean add(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode; 
        } else {
            l.next = newNode; 
        }            
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else {
            addBefore(value, getNodeByIndex(index));
        }
    }
    
    private void addBefore(T value, Node<T> insertedNode) {
        Node<T> previousNodeOfInsertedOne = insertedNode.prev;
        Node<T> newNode = new Node<>(previousNodeOfInsertedOne, value, insertedNode);
        insertedNode.prev = newNode;
        if (previousNodeOfInsertedOne == null)
            first = newNode;
        else
            previousNodeOfInsertedOne.next = newNode;
        size++;        
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
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

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
        Node<T> currentNode;
        if (index < (size / 2)) {
            currentNode = first;
            for (int i = 0; i < index; i++)
                currentNode = currentNode.next;
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--)
                currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private class Node<T> {
        Node<T> prev;
        T value;
        Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
