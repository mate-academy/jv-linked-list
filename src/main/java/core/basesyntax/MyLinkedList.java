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
    }

    @Override
    public void add(T value) {
        Node<T> tail = last;
        Node<T> newNode = new Node<>(tail, value, null);
        last = newNode;
        if (tail == null) {
            first = newNode;
        } else {
            tail.next = newNode;
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
        Node<T> prevNode = getNodeByIndex(index).prev;
        Node<T> newNode = new Node<>(prevNode, value, getNodeByIndex(index));
        getNodeByIndex(index).prev = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
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
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForRemoveGetSetMethods(index);
        T oldValue = getNodeByIndex(index).item;
        getNodeByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForRemoveGetSetMethods(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object
                    || (currentNode.item != null && currentNode.item.equals(object))) {
                remove(i);
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

    private void checkIndexForRemoveGetSetMethods(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bonds exception for index "
                    + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> nodeToUnlink) {
        final T element = nodeToUnlink.item;
        Node<T> next = nodeToUnlink.next;
        Node<T> prev = nodeToUnlink.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            nodeToUnlink.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            nodeToUnlink.next = null;
        }
        nodeToUnlink.item = null;
        size--;
        return element;
    }
}
