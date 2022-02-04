package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int sizeList = 0;

    static class Node<E> {
        private Node<E> next;
        private Node<E> prev;
        private E element;

        Node(Node<E> prev, E element,Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    public void checkElementPosition(int index) {
        if (!(index >= 0 && index <= sizeList)) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void checkElementExistence(int index) {
        if (!(index >= 0 && index < sizeList)) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void addAtTheFront(T value) {
        Node<T> newNode = new Node<>(null,value,null);
        newNode.next = head;
        newNode.prev = null;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        sizeList++;
    }

    public void addToMiddle(T value, Node<T> currentNode) {
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        sizeList++;
    }

    public void addToEnd(T value) {
        Node<T> lastListNode = tail;
        Node<T> newNode = new Node<>(tail,value,null);
        tail = newNode;
        if (lastListNode == null) {
            head = newNode;
        } else {
            lastListNode.next = newNode;
        }
        sizeList++;
    }

    @Override
    public void add(T value) {
        addToEnd(value);
    }

    @Override
    public void add(T value, int index) {
        checkElementPosition(index);
        int secondIndexList = 1;
        if (index == sizeList) {
            addToEnd(value);
        } else if (index < secondIndexList) {
            addAtTheFront(value);
        } else {
            addToMiddle(value,getCurrentNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkElementExistence(index);
        Node<T> lastListNode = tail;
        for (int i = sizeList - 1; i > index; i--) {
            lastListNode = lastListNode.prev;
        }
        return lastListNode.element;
    }

    public Node<T> getCurrentNode(int index) {
        checkElementExistence(index);
        Node<T> lastNode = tail;
        int i = sizeList - 1;
        while (i > index) {
            lastNode = lastNode.prev;
            i--;
        }
        return lastNode;
    }

    @Override
    public T set(T value, int index) {
        checkElementExistence(index);
        Node<T> nodeToBeReplaced = getCurrentNode(index);
        T replacedNodeReturn = nodeToBeReplaced.element;
        nodeToBeReplaced.element = value;
        return replacedNodeReturn;
    }

    @Override
    public T remove(int index) {
        checkElementExistence(index);
        return unlink(getCurrentNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> toBeRemoved = head;
        int index = 0;
        while (toBeRemoved.next != null && toBeRemoved.element != object) {
            toBeRemoved = toBeRemoved.next;
            index++;
            if ((toBeRemoved.element == object)
                    || (toBeRemoved.element
                    != null && toBeRemoved.element.equals(object))) {
                remove(index);
                return true;
            }
        }
        if (toBeRemoved.element == object) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    public T unlink(Node<T> nodeToBeUnlinked) {
        Node<T> previousNode = nodeToBeUnlinked.prev;
        Node<T> nextNode = nodeToBeUnlinked.next;
        if (nodeToBeUnlinked.prev == null) {
            head = nextNode;
        } else {
            nodeToBeUnlinked.prev.next = nextNode;
            nodeToBeUnlinked.prev = null;

        }
        if (nodeToBeUnlinked.next == null) {
            tail = previousNode;
        } else {
            nodeToBeUnlinked.next.prev = previousNode;
            nodeToBeUnlinked.next = null;
        }
        T elementToBeRemoved = nodeToBeUnlinked.element;

        sizeList--;
        return elementToBeRemoved;
    }

    @Override
    public int size() {
        return sizeList;
    }

    @Override
    public boolean isEmpty() {
        return sizeList == 0;
    }
}
