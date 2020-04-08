package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private MyLinkedList.Node<T> findByIndex(int index) {
        checkIndex(index);
        MyLinkedList.Node<T> node;
        if (index < size << 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i >= index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    @Override
    public boolean add(T value) {
        MyLinkedList.Node<T> tempLast = this.last;
        last = new MyLinkedList.Node<T>(value, null, tempLast);
        size++;
        if (tempLast == null) {
            first = last;
        } else {
            tempLast.next = last;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            this.add(value);
        } else {
            MyLinkedList.Node<T> node = findByIndex(index);
            MyLinkedList.Node<T> prevNode = node.prev;
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node<T>(value, node, prevNode);
            node.prev = newNode;
            if (prevNode == null) {
                first = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            this.add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        T nodeForReturn = findByIndex(index).element;
        this.remove(index);
        this.add(value, index);
        return nodeForReturn;
    }

    @Override
    public T remove(int index) {
        MyLinkedList.Node<T> node = findByIndex(index);
        T nodeForReturn = node.element;
        MyLinkedList.Node<T> prevNode = node.prev;
        MyLinkedList.Node<T> nextNode = node.next;
        if (nextNode == null && prevNode == null) {
            node.element = null;
            first = null;
            last = null;
            size--;
            return nodeForReturn;
        }
        if (prevNode == null) {
            nextNode.prev = null;
            first = nextNode;
            size--;
            return nodeForReturn;
        }
        if (nextNode == null) {
            prevNode.next = null;
            last = prevNode;
            size--;
            return nodeForReturn;
        }
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
        return nodeForReturn;
    }

    @Override
    public boolean remove(T t) {
        MyLinkedList.Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if ((t == null && t == node.element) || (t != null && t.equals(node.element))) {
                remove(i);
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
        return size == 0;
    }
}
