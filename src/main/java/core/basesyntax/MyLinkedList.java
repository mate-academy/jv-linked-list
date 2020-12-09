package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;
        
        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
    
    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
            size++;
            return true;
        } else {
            Node<T> currentNode = head;
            while (true) {
                if (currentNode.next == null) {
                    Node<T> newNode = new Node<>(currentNode, value, null);
                    currentNode.next = newNode;
                    tail = newNode;
                    size++;
                    return true;
                }
                currentNode = currentNode.next;
            }
        }
    }
    
    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> prevNode = currentNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, currentNode);
            currentNode.prev = newNode;
            prevNode.next = newNode;
            size++;
        }
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
        checkIncludeIndex(index);
        return getNode(index).value;
    }
    
    @Override
    public T set(T value, int index) {
        checkIncludeIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }
    
    @Override
    public T remove(int index) {
        checkIncludeIndex(index);
        
        //if (index == 0) {
        //    currentNode = head;
        //    Node<T> nextNode = currentNode.next;
        //    nextNode.prev = null;
        //    head = nextNode;
        //    size--;
        //    return currentNode.value;
        //}
        //if (index == size - 1) {
        //    currentNode = tail;
        //    Node<T> prevNode = currentNode.prev;
        //    prevNode.next = null;
        //    tail = currentNode;
        //    size--;
        //    return currentNode.value;
        //}
        
        Node<T> currentNode;
        if (size == 1 && index == 0) {
            head = null;
            tail = null;
            size = 0;
        }
        int i = 0;
        currentNode = head;
        while (currentNode != null) {
            if (i == index) {
                Node<T> nextNode = currentNode.next;
                Node<T> prevNode = currentNode.prev;
                if (prevNode == null) {
                    nextNode.prev = null;
                    head = nextNode;
                    size--;
                    return currentNode.value;
                }
                if (nextNode == null) {
                    prevNode.next = null;
                    tail = prevNode;
                    size--;
                    return currentNode.value;
                }
                nextNode.prev = prevNode;
                prevNode.next = nextNode;
                size--;
                return currentNode.value;
            }
            i++;
            currentNode = currentNode.next;
        }
        return null;
    }
    
    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                if (size == 1) {
                    head = null;
                    tail = null;
                    size = 0;
                    return true;
                }
                Node<T> nextNode = currentNode.next;
                Node<T> prevNode = currentNode.prev;
                if (prevNode == null) {
                    nextNode.prev = null;
                    head = nextNode;
                    size--;
                    return true;
                }
                if (nextNode == null) {
                    prevNode.next = null;
                    tail = prevNode;
                    size--;
                    return true;
                }
                nextNode.prev = prevNode;
                prevNode.next = nextNode;
                size--;
                return true;
/*
                Node<T> nextNode = currentNode.next;
                Node<T> prevNode = currentNode.prev;
                if (nextNode != null) {
                    nextNode.next = prevNode;
                } else {
                    tail = prevNode;
                }
                if (prevNode != null) {
                    prevNode.next = nextNode;
                } else {
                    head = nextNode;
                }
                size--;
                return true;
*/
            }
            currentNode = currentNode.next;
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
    
    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        int i = 0;
        while (currentNode != null) {
            if (i == index) {
                return currentNode;
            }
            i++;
            currentNode = currentNode.next;
        }
        return new Node<>(null, null, null);
    }
    
    private void checkIndex(int index) {
        if (index < 0 || size < index) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    
    private void checkIncludeIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
