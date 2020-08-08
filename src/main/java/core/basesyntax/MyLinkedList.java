package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            first = last = new Node<>(null, value, null);
            size++;
            return true;
        }
        Node<T> currentNode = new Node<>(last, value, null);
        last.next = currentNode;
        last = currentNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            addBuIndexFirstElement(value);
            return;
        }
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        Node<T> addNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = addNode;
        currentNode.prev = addNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index == size - 1) {
            return last.item;
        }
        if (index <= size / 2) {
            return iterationFromTheFirstElement(index).item;
        } else {
            return iterationFromTheLastElement(index).item;
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> returnNode = null;
        Node<T> node = null;
        if (index == 0) {
            return setFirstNode(value);
        }
        if (index >= size / 2) {
            returnNode = iterationFromTheFirstElement(index);
        } else {
            returnNode = iterationFromTheLastElement(index);
        }
        node = new Node<>(returnNode.prev, value, returnNode.next);
        returnNode.prev.next = node;
        returnNode.next.prev = node;
        return returnNode.item;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = null;
        if (index == 0) {
            return removeFirstElement().item;
        }
        if (index == size - 1) {
            return removeLastElement().item;
        }
        if (index <= size / 2) {
            removedNode = iterationFromTheFirstElement(index);
        } else {
            removedNode = iterationFromTheLastElement(index);
        }
        removedNode.prev.next = removedNode.next;
        removedNode.next.prev = removedNode.prev;
        size--;
        return removedNode.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> removedNode = null;
        if (checkEquals(first, t)) {
            removeFirstElement();
            return true;
        }
        removedNode = first;
        for (int i = 0; i < size - 1; i++) {
            removedNode = removedNode.next;
            if (checkEquals(removedNode, t)) {
                removedNode.prev.next = removedNode.next;
                removedNode.next.prev = removedNode.prev;
                size--;
                return true;
            }
            if (i == size - 1 && checkEquals(last, t)) {
                removeLastElement();
                return true;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean checkEquals(Node<T> node, T value) {
        return (node.item == value || node.item != null && node.item.equals(value));
    }

    private Node<T> iterationFromTheFirstElement(int index) {
        Node<T> node = first;
        for (int i = 1; i <= index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node<T> iterationFromTheLastElement(int index) {
        Node<T> node = last;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private Node<T> removeLastElement() {
        last = last.prev;
        last.next = null;
        size--;
        return last;
    }

    private Node<T> removeFirstElement() {
        Node<T> node = first;
        if (size == 1) {
            first = last = null;
            size--;
            return node;
        }
        first.next.prev = null;
        first = first.next;
        size--;
        return node;
    }

    private T setFirstNode(T value) {
        Node<T> returnNode = first;
        ;
        Node<T> node = new Node<>(null, value, first.next);
        first.next.prev = node;
        first = node;
        return returnNode.item;
    }

    private void addBuIndexFirstElement(T value) {
        Node<T> node = new Node<>(null, value, first);
        first.prev = node;
        first = node;
        size++;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}

