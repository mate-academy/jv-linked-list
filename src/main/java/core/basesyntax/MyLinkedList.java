package core.basesyntax;

import java.util.LinkedList;
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
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = findNodeByIndex(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return  oldValue;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Your index is out of range");
        }
    return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> removeNode = head; removeNode != null; removeNode = removeNode.next) {
                if (removeNode.value == null) {
                    unlink(removeNode);
                    return true;
                }
            }
        } else {
            for (Node<T> removeNode = head; removeNode != null; removeNode = removeNode.next) {
                if (object.equals(removeNode.value)) {
                    unlink(removeNode);
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
    private T unlink(Node<T> unlinkNode ) {
        T element = unlinkNode.value;
        Node<T> next = unlinkNode.next;
        Node<T> prev = unlinkNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            unlinkNode.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            unlinkNode.next = null;
        }
        unlinkNode.value = null;
        size--;
        return element;

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
}
