package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexCheck(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        Node<T> nextNode = getNodeByIndex(index);
        Node<T> prevNode = nextNode.prev;
        newNode.next = nextNode;
        newNode.prev = prevNode;
        prevNode.next = newNode;
        nextNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        Node<T> node = getNodeByIndex(index);
        return node == null ? null : node.element;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> node = getNodeByIndex(index);
        if (node == null) {
            return null;
        }
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        return unlink(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = head;
        while (nodeToRemove != null) {
            if (object == nodeToRemove.element
                    || object != null && object.equals(nodeToRemove.element)) {
                break;
            }
            nodeToRemove = nodeToRemove.next;
        }
        unlink(nodeToRemove);
        return nodeToRemove == null ? false : true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private T unlink(Node<T> nodeToRemove) {
        if (nodeToRemove == null) {
            return null;
        }
        if (nodeToRemove.prev == null) {
            deleteFirstNode();
            return nodeToRemove.element;
        }
        if (nodeToRemove.next == null) {
            deleteLastNode();
            return nodeToRemove.element;
        }
        deleteNode(nodeToRemove);
        return nodeToRemove.element;
    }

    private T deleteFirstNode() {
        Node<T> deletedNode = head;
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return deletedNode.element;
        }
        head.next.prev = null;
        head = head.next;
        size--;
        return deletedNode.element;
    }

    private T deleteLastNode() {
        Node<T> deletedNode = tail;
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return deletedNode.element;
        }
        tail.prev.next = null;
        tail = tail.prev;
        size--;
        return deletedNode.element;
    }

    private T deleteNode(Node<T> nodeToRemove) {
        nodeToRemove.next.prev = nodeToRemove.prev;
        nodeToRemove.prev.next = nodeToRemove.next;
        size--;
        return nodeToRemove.element;
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByIndex(int index) {
        return index > size / 2 ? goFromTailToHead(index) : goFromHeadToTail(index);
    }

    private Node<T> goFromTailToHead(int index) {
        int currentIndex = size - 1;
        Node<T> currentNode = tail;
        while (currentNode != null) {
            if (index == currentIndex) {
                return currentNode;
            }
            currentNode = currentNode.prev;
            currentIndex--;
        }
        return null;
    }

    private Node<T> goFromHeadToTail(int index) {
        int currentIndex = 0;
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (index == currentIndex) {
                return currentNode;
            }
            currentNode = currentNode.next;
            currentIndex++;
        }
        return null;
    }

    private static class Node<T> {
        T element;
        Node<T> prev;
        Node<T> next;

        Node(Node<T>prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
