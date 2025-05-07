package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;

        size++;
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

        unlink(findedNode);

        size--;

        return findedNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;

        while (currentNode != null) {
            if (currentNode.element != null ? currentNode.element.equals(object) : object == null) {
                unlink(currentNode);

                size--;

                return true;
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
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        outOfBoundsCheck(index);

        boolean startFromTail = index * 2 >= size;

        Node<T> currentNode = startFromTail ? tail : head;

        int indexCounter = startFromTail ? size - 1 : 0;

        while (currentNode != null) {
            if (indexCounter == index) {
                return currentNode;
            }

            currentNode = startFromTail ? currentNode.prev : currentNode.next;
            indexCounter = startFromTail ? indexCounter - 1 : indexCounter + 1;
        }

        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
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

    private void unlink(Node<T> nodeToUnlink) {
        manageHead(nodeToUnlink, nodeToUnlink.next);
        manageTail(nodeToUnlink, nodeToUnlink.prev);

        nodeToUnlink.prev = null;
        nodeToUnlink.next = null;
    }
}
