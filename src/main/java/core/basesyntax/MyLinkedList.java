package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(null, value, null);
            first = node;
            last = node;
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is not correct");
        }
        if (size == index) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> node = new Node<>(null, value, first);
            first.prev = node;
            first = node;
        } else {
            Node<T> findedNode = findNodeByIndex(index);
            Node<T> previous = findedNode.prev;
            Node<T> newNode = new Node<>(previous, value, findedNode);
            previous.next = newNode;
            findedNode.prev = newNode;
        }
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
        return findNodeByIndex(index).element;

    }

    @Override
    public T set(T value, int index) {
        Node<T> changeNode = findNodeByIndex(index);
        T oldElement = changeNode.element;
        changeNode.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        Node<T> deleteNode = findNodeByIndex(index);
        unlink(deleteNode);
        return deleteNode.element;
    }

    @Override
    public boolean remove(T t) {
        Node<T> deleteNode = first;
        for (int i = 0; i < size; i++) {
            if (deleteNode.element == t || t != null && deleteNode.element.equals(t)) {
                unlink(deleteNode);
                return true;
            }
            deleteNode = deleteNode.next;
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

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            first = nextNode;

        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
    }
}
