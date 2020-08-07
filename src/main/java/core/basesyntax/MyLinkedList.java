package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private static class Node<T1> {
        T1 storedItem;
        Node<T1> previous;
        Node<T1> next;

        public Node(T1 element) {
            this.storedItem = element;
        }
    }

    public void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void nodeInsertion(Node<T> previousNode, Node<T> nodeToInsert, Node<T> nextNode) {
        if (previousNode == null) {
            nodeToInsert.next = nextNode;
            nodeToInsert.previous = null;
            nextNode.previous = nodeToInsert;
            head = nodeToInsert;
        } else {
            nodeToInsert.next = nextNode;
            nodeToInsert.previous = previousNode;
            previousNode.next = nodeToInsert;
            nextNode.previous = nodeToInsert;
        }
    }

    public void removeOnIndex(int nodePosition, Node<T> nodeToRemove) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (nodePosition == 0) {
            nodeToRemove.next.previous = null;
            head = nodeToRemove.next;
        } else if (nodePosition == size - 1) {
            nodeToRemove.previous.next = null;
            tail = nodeToRemove.previous;
        } else {
            nodeToRemove.previous.next = nodeToRemove.next;
            nodeToRemove.next.previous = nodeToRemove.previous;
        }
        nodeToRemove.next = nodeToRemove.previous = null;
    }

    public Node<T> findNode(int index) {
        Node<T> headHandler = head;
        Node<T> tailHandler = tail;
        if (index < size / 2) {
            for (int i = 0; i < index && headHandler.next != null; i++) {
                headHandler = headHandler.next;
            }
            return headHandler;
        } else {
            for (int i = size - 1; i > index && tailHandler.previous != null; i--) {
                tailHandler = tailHandler.previous;
            }
            return tailHandler;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> nodeToAdd = new Node<>(value);
        if (head == null) {
            nodeToAdd.previous = null;
            nodeToAdd.next = null;
            head = nodeToAdd;
            tail = nodeToAdd;
        } else {
            nodeToAdd.next = null;
            nodeToAdd.previous = tail;
            tail.next = nodeToAdd;
            tail = nodeToAdd;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkBounds(index);
        nodeInsertion(findNode(index).previous, new Node<>(value), findNode(index));

        size++;
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
        checkBounds(index);
        return findNode(index).storedItem;
    }

    @Override
    public T set(T value, int index) {
        checkBounds(index);
        Node<T> nodeInPlace = findNode(index);
        T elementToReplace = nodeInPlace.storedItem;
        nodeInPlace.storedItem = value;
        return elementToReplace;
    }

    @Override
    public T remove(int index) {
        checkBounds(index);
        Node<T> nodeToRemove = findNode(index);
        removeOnIndex(index, nodeToRemove);
        --size;
        return nodeToRemove.storedItem;
    }

    @Override
    public boolean remove(T t) {
        Node<T> holder = head;
        int i = 0;
        while (i < size && holder.next != null && !(holder.storedItem == t
                || (holder.storedItem != null && holder.storedItem.equals(t)))) {
            holder = holder.next;
            i++;
        }
        if (holder.storedItem == t || (holder.storedItem != null && holder.storedItem.equals(t))) {
            removeOnIndex(i, holder);
            size--;
            return true;
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
}
