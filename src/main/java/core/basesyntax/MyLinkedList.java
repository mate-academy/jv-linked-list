package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> headNode;
    private Node<T> tailNode;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tailNode, null);
        if (tailNode == null) {
            headNode = newNode;
        } else {
            tailNode.next = newNode;
        }
        tailNode = newNode;
        size++;
    }
    
    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> findNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(value, findNode.prev, findNode);
        if (index == 0) {
            headNode.prev = newNode;
            headNode = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        findNode.prev = newNode;
        size++;
    }
    
    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }
    
    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).item;
    }
    
    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = findNodeByIndex(index);
        T oldItem = newNode.item;
        newNode.item = value;
        return oldItem;
    }
    
    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        unlink(node);
        size--;
        return node.item;
    }
    
    @Override
    public boolean remove(T object) {
        Node<T> node = headNode;
        if (size == 0) {
            return false;
        } 
        while (node != null) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
                unlink(node);
                size--;
                return true;
            }
            node = node.next;
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

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            headNode = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tailNode = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> node = headNode;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Input index: " + index + " out of bound: "
                    + size);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }        
    }
}
