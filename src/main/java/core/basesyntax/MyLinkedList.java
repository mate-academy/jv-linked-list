package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Index out of bounds";
    private Node<T> first;
    private Node<T> last;
    private int size;

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void setLast(Node<T> last) {
        this.last = last;
    }

    @Override
    public boolean add(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(oldLast, value, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode; 
        } else {
            oldLast.next = newNode;
        }            
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndexNotInclusive(index);
        if (index == size) {
            add(value);
        } else {
            addBefore(value, getNodeByIndex(index));
        }
    }
    
    private void addBefore(T value, Node<T> insertedNode) {
        Node<T> previousNodeOfInsertedOne = insertedNode.previous;
        Node<T> newNode = new Node<>(previousNodeOfInsertedOne, value, insertedNode);
        insertedNode.previous = newNode;
        if (previousNodeOfInsertedOne == null) {
            first = newNode;
        } else {
            previousNodeOfInsertedOne.next = newNode;
        }
        size++;        
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexNotInclusive(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) == object || get(i).equals(object)) {
                unlink(getNodeByIndex(i));
                return true;
            }
        }
        return false;
    }

    private T unlink(Node<T> nodeToRemove) {
        Node<T> nextOfNodeToRemove = nodeToRemove.next;
        Node<T> previousOfNodeToRemove = nodeToRemove.previous;
        if (previousOfNodeToRemove == null) {
            first = nextOfNodeToRemove;
        } else {
            previousOfNodeToRemove.next = nextOfNodeToRemove;
            nodeToRemove.previous = null;
        }
        if (nextOfNodeToRemove == null) {
            last = previousOfNodeToRemove;
        } else {
            nextOfNodeToRemove.previous = previousOfNodeToRemove;
            nodeToRemove.next = null;
        }
        T nodeToRemoveValue = nodeToRemove.value;
        nodeToRemove.value = null;
        size--;
        return nodeToRemoveValue;
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
        checkIndex(index);
        Node<T> currentNode;
        if (index < (size / 2)) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }

    private void checkIndexNotInclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }

    private class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.previous = prev;
            this.value = value;
            this.next = next;
        }
    }
}
