package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> firstNode;
    private Node<T> lastNode;
    private int listLength = 0;

    @Override
    public void add(T value) {
        if (listLength > 0) {
            final Node<T> tempNode = lastNode;
            lastNode = new Node<>(tempNode, value, null);
            tempNode.next = lastNode;
            listLength++;
        } else {
            firstNode = new Node<>(null, value, null);
            lastNode = firstNode;
            listLength++;
        }
    }

    @Override
    public void add(T value, int index) {
        correctIndex(index);
        if (index > 0 && index < listLength) {
            Node<T> tempNode = getNode(index);
            Node<T> newNode = new Node<>(tempNode.prev, value, tempNode);
            tempNode.prev.next = newNode;
            tempNode.prev = newNode;
            listLength++;
        }
        if (index == 0 && listLength > 0) {
            Node<T> tempNode = new Node<>(null, value, firstNode);
            firstNode = tempNode;
            listLength++;
        }
        if ((lastNode == null || listLength > 0) && index == listLength) {
            add(value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < listLength) {
            return getNode(index).item;
        }
        throw new IndexOutOfBoundsException("Index " + index + " is no correct!!!");
    }

    @Override
    public T set(T value, int index) {
        if (index >= 0 && index < listLength) {
            Node<T> tempNode = getNode(index);
            T oldValue = tempNode.item;
            tempNode.item = value;
            return oldValue;
        }
        throw new IndexOutOfBoundsException("Index " + index + " is no correct!!!");
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < listLength) {
            Node<T> tempNode = getNode(index);
            T oldValue = tempNode.item;
            if (tempNode.prev == null && tempNode.next == null) {
                lastNode = null;
                firstNode = null;
                listLength--;
                return oldValue;
            }
            if (tempNode.prev == null) {
                tempNode.next.prev = null;
                firstNode = tempNode.next;
                listLength--;
                return oldValue;
            }
            if (tempNode.next == null) {
                tempNode.prev.next = null;
                lastNode = tempNode.prev;
                listLength--;
                return oldValue;
            }
            tempNode.next.prev = tempNode.prev;
            tempNode.prev.next = tempNode.next;
            listLength--;
            return oldValue;
        }
        throw new IndexOutOfBoundsException("Index " + index + " is no correct!!!");
    }

    @Override
    public boolean remove(T object) {

        Node<T> tempNode = firstNode;
        for (int i = 0; i < size(); i++) {
            if (object == tempNode.item || (object != null && object.equals(tempNode.item))) {
                remove(i);
                return true;
            }
            tempNode = tempNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return listLength;
    }

    @Override
    public boolean isEmpty() {
        return listLength == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void correctIndex(int index) {
        if (index < 0 || index > listLength) {
            throw new IndexOutOfBoundsException("Index " + index + " is no correct!!!");
        }
    }

    private Node<T> getNode(int index) {
        boolean beginOrEnd = index <= listLength / 2;
        Node<T> tempNode = beginOrEnd ? firstNode : lastNode;
        for (int i = 0; i < (beginOrEnd ? index : listLength - index - 1); i++) {
            tempNode = beginOrEnd ? tempNode.next : tempNode.prev;
        }
        return tempNode;
    }
}
