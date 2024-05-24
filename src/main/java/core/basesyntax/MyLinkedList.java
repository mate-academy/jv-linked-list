package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        addToLastPosition(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0) {
            addToFirstPosition(value);
        } else if (index == size) {
            addToLastPosition(value);
        } else {
            addInsideList(index, value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            addToLastPosition(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGet(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.getValue();
        currentNode.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        removeNode(currentNode);
        return currentNode.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNodeByValue(object);
        return removeNode(currentNode);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addToFirstPosition(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        if (head != null) {
            head.setPrev(newNode);
        } else {
            tail = newNode;
        }
        head = newNode;
        size++;
    }

    private void addToLastPosition(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail != null) {
            tail.setNext(newNode);
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    private void addInsideList(int index, T value) {
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        addNodeInsideList(newNode, currentNode);
    }

    private void addNodeInsideList(Node<T> newNode, Node<T> oldNode) {
        if (oldNode == head) {
            addToFirstPosition(newNode.getValue());
        } else {
            newNode.setPrev(oldNode.getPrev());
            oldNode.getPrev().setNext(newNode);
            oldNode.setPrev(newNode);
            newNode.setNext(oldNode);
        }
        size++;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || (size == 0 ? index > 0 : index > size)) {
            throw new IndexOutOfBoundsException("incorrect index");
        }
    }

    private void checkIndexForGet(int index) {
        if (index < 0 || (size == 0 ? index > 0 : index >= size)) {
            throw new IndexOutOfBoundsException("incorrect index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndexForGet(index);
        int count = 0;
        Node<T> currentNode = head;
        while (currentNode.getNext() != null) {
            if (index == count++) {
                break;
            }
            currentNode = currentNode.getNext();
        }
        return currentNode;
    }

    private boolean removeNode(Node<T> currentNode) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode == head && currentNode == tail) {
            head = null;
            tail = null;
        } else if (currentNode == head) {
            if (currentNode.getNext() != null) {
                currentNode.getNext().setPrev(null);
            }
            head = currentNode.getNext();
        } else if (currentNode == tail) {
            currentNode.getPrev().setNext(null);
            tail = currentNode.getPrev();
        } else {
            currentNode.getNext().setPrev(currentNode.getPrev());
            currentNode.getPrev().setNext(currentNode.getNext());
        }
        size--;
        return true;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (value != null
                    && value.equals(currentNode.getValue()) || value == currentNode.getValue()) {
                return currentNode;
            }
            currentNode = currentNode.getNext();
        }
        return null;
    }
}
