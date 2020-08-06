package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;
    private Node<T> node;

    public MyLinkedList() {
        node = null;
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
        node = new Node<>(last, value, null);
        last.next = node;
        last = node;
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
            node = new Node<>(null, value, first);
            first.prev = node;
            first = node;
            size++;
            return;
        }
        node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        Node<T> addNode = new Node<>(node.prev, value, node);
        node.prev.next = addNode;
        node.prev = addNode;
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
        iterationFromTheFirstElement(index);
        iterationFromTheLastElement(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> returnNode = null;
        if (index == 0) {
            node = new Node<>(null, value, first.next);
            returnNode = first;
            first.next.prev = node;
            first = node;
            return returnNode.item;
        }
        if (size - index > size / 2) {
            returnNode = first;
            for (int i = 1; i <= index; i++) {
                returnNode = returnNode.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i >= index; i--) {
                returnNode = returnNode.prev;
            }
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
            node = first;
            if (size == 1) {
                first = last = null;
                size--;
                return node.item;
            }
            first.next.prev = null;
            first = first.next;
            size--;
            return node.item;
        }
        if (index == size - 1) {
            node = last;
            last = last.prev;
            last.next = null;
            size--;
            return node.item;
        }
        iterationFromTheFirstElement(index);
        iterationFromTheLastElement(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.item;
    }

    @Override
    public boolean remove(T t) {

        if (checkEquals(first, t)) {
            if (size == 1) {
                first = last = null;
                size--;
                return true;
            }
            first.next.prev = null;
            first = first.next;
            size--;
            return true;
        }
        node = first;
        for (int i = 0; i < size - 1; i++) {
            node = node.next;
            if (checkEquals(node, t)) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
                return true;
            }
            if (i == size - 1 && checkEquals(last, t)) {
                node = last;
                last = last.prev;
                last.next = null;
                size--;
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

    private boolean iterationFromTheFirstElement(int index) {
        if (size - index > size / 2) {
            node = first;
            for (int i = 1; i <= index; i++) {
                node = node.next;
            }
            return true;
        }
        return false;
    }

    private boolean iterationFromTheLastElement(int index) {
        if (size - index <= size / 2) {
            node = first;
            for (int i = 1; i <= index; i++) {
                node = node.next;
            }
            return true;
        }
        return false;
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

