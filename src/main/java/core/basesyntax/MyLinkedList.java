package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> tail = getLast();
        Node<T> newNode = new Node<>(tail, value, null);
        setLast(newNode);
        if (tail == null) {
            setFirst(newNode);
        } else {
            tail.setNext(newNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bonds exception for index "
                    + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> prevNode = getNodeByIndex(index).getPrev();
        Node<T> newNode = new Node<>(prevNode, value, getNodeByIndex(index));
        getNodeByIndex(index).setPrev(newNode);
        if (prevNode == null) {
            setFirst(newNode);
        } else {
            prevNode.setNext(newNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForRemoveGetSetMethods(index);
        return getNodeByIndex(index).getItem();
    }

    @Override
    public T set(T value, int index) {
        checkIndexForRemoveGetSetMethods(index);
        T oldValue = getNodeByIndex(index).getItem();
        getNodeByIndex(index).setItem(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForRemoveGetSetMethods(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getFirst();
        for (int i = 0; i < size; i++) {
            if (currentNode.getItem() == object
                    || (currentNode.getItem() != null && currentNode.getItem().equals(object))) {
                remove(i);
                return true;
            }
            currentNode = currentNode.getNext();
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

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void setLast(Node<T> last) {
        this.last = last;
    }

    private void checkIndexForRemoveGetSetMethods(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bonds exception for index "
                    + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = getFirst();
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.getNext();
            }
        } else {
            currentNode = getLast();
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.getPrev();
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> nodeToUnlink) {
        final T element = nodeToUnlink.getItem();
        Node<T> next = nodeToUnlink.getNext();
        Node<T> prev = nodeToUnlink.getPrev();
        if (prev == null) {
            setFirst(next);
        } else {
            prev.setNext(next);
            nodeToUnlink.setPrev(null);
        }
        if (next == null) {
            setLast(prev);
        } else {
            next.setPrev(prev);
            nodeToUnlink.setNext(null);
        }
        nodeToUnlink.setItem(null);
        size--;
        return element;
    }
}
