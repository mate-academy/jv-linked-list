package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public MyLinkedList(List<T> list) {
        this.size = list.size();

        addAll(list);
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (this.head == null) {
            this.head = newNode;
        } else {
            tail.next = newNode;
        }

        this.tail = newNode;

        this.size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }

        outOfBoundsCheck(index);

        Node<T> findedNode = getNodeByIndex(index);

        Node<T> newNode = new Node<>(findedNode.prev, value, findedNode);

        manageHead(findedNode, newNode);

        findedNode.prev = newNode;

        this.size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        outOfBoundsCheck(index);

        Node<T> findedNode = getNodeByIndex(index);

        return findedNode.element;
    }

    @Override
    public T set(T value, int index) {
        outOfBoundsCheck(index);

        Node<T> findedNode = getNodeByIndex(index);

        Node<T> newNode = new Node<>(findedNode.prev, value, findedNode.next);

        manageHead(findedNode, newNode);

        manageTail(findedNode, newNode);

        return findedNode.element;
    }

    @Override
    public T remove(int index) {
        outOfBoundsCheck(index);

        Node<T> findedNode = getNodeByIndex(index);

        manageHead(findedNode, findedNode.next);

        manageTail(findedNode, findedNode.prev);

        this.size--;

        return findedNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        int indexCounter = 0;

        while (currentNode != null) {
            if (currentNode.element != null ? currentNode.element.equals(object) : object == null) {
                remove(indexCounter);

                return true;
            }

            currentNode = currentNode.next;
            indexCounter++;
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

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.element = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private void outOfBoundsCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByIndex(int index) {
        outOfBoundsCheck(index);

        Node<T> currentNode = head;
        int indexCounter = 0;

        while (currentNode != null) {
            if (indexCounter == index) {
                return currentNode;
            }

            currentNode = currentNode.next;
            indexCounter++;
        }

        throw new IndexOutOfBoundsException();
    }

    private void manageHead(Node<T> oldPointer, Node<T> newPointer) {
        if (oldPointer != head) {
            oldPointer.prev.next = newPointer;
        } else {
            this.head = newPointer;
        }
    }

    private void manageTail(Node<T> oldPointer, Node<T> newPointer) {
        if (oldPointer != tail) {
            oldPointer.next.prev = newPointer;
        } else {
            this.tail = newPointer;
        }
    }
}
