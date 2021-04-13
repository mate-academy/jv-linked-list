package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> nodeTail;
    private Node<T> nodeHead;
    private int sizeOfList;

    private static class Node<I> {
        private I element;
        private Node<I> previous;
        private Node<I> next;

        Node(Node<I> previous, I element, Node<I> next) {
            this.next = next;
            this.previous = previous;
            this.element = element;
        }
    }

    @Override
    public boolean add(T value) {
        if (sizeOfList == 0) {
            nodeTail = new Node<>(null,value,null);
            nodeHead = nodeTail;
            sizeOfList++;
            return true;
        }
        Node<T> newNode = new Node<>(nodeTail,value,null);
        nodeTail.next = newNode;
        nodeTail = newNode;
        sizeOfList++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == sizeOfList) {
            add(value);
            return;
        }
        checkedIndex(index);
        if (index == 0) {
            Node<T> currentNode = new Node<>(null,value,nodeHead);
            nodeHead.previous = currentNode;
            nodeHead = currentNode;
            sizeOfList++;
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.previous, value, currentNode);
        currentNode.previous.next = newNode;
        currentNode.previous = newNode;
        sizeOfList++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T valueOfList : list) {
            add(valueOfList);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkedIndex(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkedIndex(index);
        Node<T> currentValue = findNodeByIndex(index);
        T changedValue = currentValue.element;
        currentValue.element = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        checkedIndex(index);
        Node<T> removedNode = findNodeByIndex(index);
        final T removedValue = removedNode.element;
        if (removedNode.next == null) {
            nodeTail = removedNode.previous;
        } else {
            removedNode.next.previous = removedNode.previous;
        }
        if (removedNode.previous == null) {
            nodeHead = removedNode.next;
        } else {
            removedNode.previous.next = removedNode.next;
        }
        sizeOfList--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = nodeHead;
        for (int i = 0; i < sizeOfList; i++) {
            if (object == current.element || object != null && object.equals(current.element)) {
                remove(i);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return sizeOfList;
    }

    @Override
    public boolean isEmpty() {
        return sizeOfList == 0;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index <= sizeOfList / 2) {
            currentNode = nodeHead;
            int i = 0;
            while (i < index) {
                currentNode = currentNode.next;
                i++;
            }
            return currentNode;
        }
        currentNode = nodeTail;
        int i = sizeOfList - 1;
        while (i > index) {
            currentNode = currentNode.previous;
            i--;
        }
        return currentNode;
    }

    private void checkedIndex(int index) {
        if (index >= sizeOfList || index < 0) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        }
    }
}
