package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        this.size = 0;
    }

    @Override
    public boolean add(T value) {
        addToTheEnd(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkStrictlyIndex(index);
        if (index == size) {
            addToTheEnd(value);
        } else {
            addBefore(value, getNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        boolean isAdded = false;
        if (list.size() != 0) {
            isAdded = true;
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
        return isAdded;
    }

    @Override
    public T get(int index) {
        checkGentlyIndex(index);
        Node<T> node = getNode(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkGentlyIndex(index);
        Node<T> newSetNode = getNode(index);
        T oldValue = newSetNode.item;
        newSetNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkGentlyIndex(index);
        Node<T> nodeForRemove = getNode(index);
        T removedNodeValue = nodeForRemove.item;
        unlink(nodeForRemove);
        return removedNodeValue;
    }

    @Override
    public boolean remove(T object) {
        Node nodeForRemove;
        if (object == null) {
            for (nodeForRemove = head; nodeForRemove != null;
                    nodeForRemove = nodeForRemove.next) {
                if (nodeForRemove.item == null) {
                    unlink(nodeForRemove);
                    return true;
                }
            }
        } else {
            for (nodeForRemove = head; nodeForRemove != null; nodeForRemove = nodeForRemove.next) {
                if (object.equals(nodeForRemove.item)) {
                    unlink(nodeForRemove);
                    return true;
                }
            }
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

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prevElement, E element, Node<E> nextElement) {
            prev = prevElement;
            item = element;
            next = nextElement;
        }
    }

    private void addToTheEnd(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }

        size++;
    }

    private void addBefore(T value, Node<T> nextNode) {
        Node<T> previous = nextNode.prev;
        Node<T> newNode = new Node(previous, value, nextNode);
        nextNode.prev = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }

        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            current = tail;
            for (int j = size - 1; j > index; j--) {
                current = current.prev;
            }
            return current;
        }
    }

    private T unlink(Node<T> nodeToRemove) {
        Node<T> next = nodeToRemove.next;
        Node<T> prev = nodeToRemove.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            nodeToRemove.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            nodeToRemove.next = null;
        }

        T element = nodeToRemove.item;
        nodeToRemove.item = null;
        size--;
        return element;
    }

    private void checkStrictlyIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound");
        }
    }

    private void checkGentlyIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("This index " + index + " is out of bound");
        }
    }
}
