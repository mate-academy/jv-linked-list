package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkAsTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index
                    + " for list with size: " + size);
        }
        if (index == 0) {
            linkAsHead(value);
            return;
        } else if (index == size) {
            linkAsTail(value);
            return;
        }
        Node<T> successorNode = getNodeByIndex(index);
        linkBefore(value, successorNode);
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            linkAsTail(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T tempValue = node.value;
        node.value = value;
        return tempValue;
    }

    @Override
    public T remove(int index) {
        return unLink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> objectNode = getNodeByValue(object);
        if (objectNode == null) {
            return false;
        }
        unLink(objectNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isIncorrectIndex(int index) {
        return index >= size || index < 0;
    }

    private boolean isCloserToHead(int index) {
        return index <= size >> 1;
    }

    private void linkAsHead(T value) {
        Node<T> newNode = new Node<>(null,value, head);
        if (head == null) {
            tail = newNode;
        } else {
            head.prev = newNode;
        }
        head = newNode;
        size++;
    }

    private void linkAsTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void linkBefore(T value, Node<T> successorNode) {
        Node<T> newNode = new Node<>(successorNode.prev, value, successorNode);
        successorNode.prev.next = newNode;
        successorNode.prev = newNode;
        size++;
    }

    private T unLink(Node<T> node) {
        if (node.prev == null) {
            return unLinkHead();
        }
        if (node.next == null) {
            return unLinkTail();
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        T tempValue = node.value;
        node.value = null;
        size--;
        return tempValue;
    }

    private T unLinkHead() {
        Node<T> oldHead = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        oldHead.next = null;
        T tempObject = oldHead.value;
        oldHead.value = null;
        size--;
        return tempObject;
    }

    private T unLinkTail() {
        Node<T> oldTail = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        oldTail.prev = null;
        T temporaryObject = oldTail.value;
        oldTail.value = null;
        size--;
        return temporaryObject;
    }

    private Node<T> getNodeByIndex(int index) {
        if (isIncorrectIndex(index)) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index
                    + " for list with size: " + size);
        }
        Node<T> tempNode;
        if (isCloserToHead(index)) {
            tempNode = head;
            for (int i = 0; i != index; i++) {
                tempNode = tempNode.next;
            }
        } else {
            tempNode = tail;
            for (int i = size - 1; i != index; i--) {
                tempNode = tempNode.prev;
            }
        }
        return tempNode;
    }

    private Node<T> getNodeByValue(T value) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (value != null && value.equals(node.value) || node.value == null && value == null) {
                return node;
            }
        }
        return null;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
