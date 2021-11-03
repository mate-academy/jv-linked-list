package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        Node(Node<T> prev,T item, Node<T> next){
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;
    
    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if(size == 0) {
            firstNode = node;
        } else {
            node.prev = lastNode;
            lastNode.next = node;
        }
        lastNode = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            createNewNode(value);
            return;
        }
        Node<T> nodeFromIndex = getNodefromIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = nodeFromIndex;
            nodeFromIndex.prev = newNode;
            firstNode = newNode;
        } else {
            newNode.prev = nodeFromIndex.prev;
            newNode.next = nodeFromIndex;
            nodeFromIndex.prev.next = newNode;
            nodeFromIndex.prev = newNode;
        }
        size++;
        }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getItemFromIndex(index);
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getItemFromIndex(index);
        getNodefromIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodefromIndex(index);
       removeNode(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = firstNode;
        while (node != null) {
            if (node.item == object|| node.item != null && node.item.equals(object)) {
               removeNode(node);
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

    private void createNewNode(T item) {
        Node<T> node = new Node<>(lastNode, item, null);
        if (lastNode != null) {
            lastNode.next = node;
        }
        lastNode = node;
        if(firstNode == null) {
            firstNode = node;
        }
        size++;
    }

    private void chekForTrueIndex(int index) {
        if (index < 0 || index >= size ) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodefromIndex(int index) {
        chekForTrueIndex(index);
        Node<T> node = firstNode;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                break;
            } else {
                node = node.next;
            }
        }
        return node;
    }

    private T getItemFromIndex(int index) {
            return getNodefromIndex(index).item;
    }

    private T setItemToNode(int index, T value) {
        chekForTrueIndex(index);
        Node<T> node;
        for (int i = 0; i <= index; i++) {
            node = firstNode.next;
            if (i == index) {
                node.item = value;
            }
        }
        return null;
    }
    private void removeNode(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous == null) {
            firstNode = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            lastNode = previous;
        } else {
            next.prev = previous;
        }
        size--;
    }
}
