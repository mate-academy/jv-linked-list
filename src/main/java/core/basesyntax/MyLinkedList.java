package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode= new Node<>(null, value,null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Your index is out of range");
        }
        if (head == null || index == size) {
            add(value);
            return;
        }
        else if(index == 0) {
            Node<T> insertNode = new Node<>(null,value,head);
            head.prev = insertNode;
            head = insertNode;
        } else {
            Node<T> refNode = findNodeByIndex(index);
            Node<T> insertNode = new Node<>(refNode.prev,value,refNode);
            refNode.prev.next = insertNode;
            refNode.prev = insertNode;
           }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = findNodeByIndex(index);
        T oldValue = oldNode.getValue();
        oldNode.setValue(value);
        return  oldValue;
    }

    @Override
    public T remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Your index is out of range");
        }
        size--;
        Node<T> deleteNode = findNodeByIndex(index);
        if (index != 0 && index != size) {
            unlink(deleteNode);
        }
        if (index == 0) {
            head = deleteNode.next;
            deleteNode.next.prev = null;
        }
        return deleteNode.getValue();
    }

    @Override
    public boolean remove(T object) {
       if (head == null || object == null) {
           return false;}
        if (head == tail && head.equals(object)){
           head = tail = null;
           size--;
           return true;
       } else
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
    private void unlink(Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Your index is out of range");
        }
    }
    private Node<T> findNodeByIndex(int index) {
        int count = 0;
        Node<T> findNode = head;
        while (count != index){
            findNode = findNode.next;
            count++;
        }
        return findNode;
    }
    private Node<T> findByObject(T object) {
        boolean objectFound = false;
        Node<T> removeNode = head;
        if ( removeNode == null) {
            throw new NoSuchElementException("Your object is null");
        }
        while (!objectFound) {
            if (removeNode.getValue().equals(object)
                    || removeNode.getValue() == object) {
                objectFound = true;
            }
            else {
                removeNode = removeNode.next;
            }
        }
        return removeNode;
    }
}
