package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node firstNode;
    private Node lastNode;

    public MyLinkedList() {
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index doesn't exist:" + index);
        }
    }

    private void checkElementPosition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Uncorrect index:" + index);
        }
    }

    private Node getElement(int index) {
        MyLinkedList.Node<T> first = firstNode;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return first;
            }
            first = first.next;
        }
        return null;
    }

    private void unlinkNode(Node node) {
        if (node.next != null && node.prev != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else if (node.next == null && node.prev == null) {
            lastNode = null;
            firstNode = null;
        } else if (node.next == null) {
            lastNode = node.prev;
            node.prev.next = null;
        } else if (node.prev == null) {
            firstNode = node.next;
            node.next.prev = null;
        }
        size--;
    }

    @Override
    public void add(T value) {
        MyLinkedList.Node<T> last = lastNode;
        MyLinkedList.Node<T> newNode = new Node<>(last, value, null);
        lastNode = newNode;
        if (last == null) {
            firstNode = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkElementPosition(index);
        Node node = getElement(index);
        if (node == null) {
            add(value);
        } else if (node != null) {
            MyLinkedList.Node<T> newNode = new Node<>(node.prev, value, node);
            if (node.prev != null) {
                node.prev.next = newNode;
                node.prev = newNode;
            } else {
                newNode.prev = null;
                node.prev = newNode;
                firstNode = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return (T) getElement(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Object beforeChange = getElement(index).item;
        getElement(index).item = value;
        return (T) beforeChange;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        final Node removedNode = getElement(index);
        unlinkNode(getElement(index));
        return (T) removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node node = firstNode;
        for (int i = 0; i < size; i++) {
            if (node.item != null && node.item.equals(object)
                    || node.item == null && object == null) {
                unlinkNode(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null && lastNode == null;
    }
}
