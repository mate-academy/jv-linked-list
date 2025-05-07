package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Wrong index - " + index + ", try again !");
        }
        if (index == size()) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            if (index == 0) {
                if (first != null) {
                    newNode.next = first;
                    first.prev = newNode;
                    first = newNode;
                }
            } else {
                Node<T> currentNode = lookForNode(index - 1);
                newNode.next = currentNode.next;
                newNode.prev = currentNode;
                currentNode.next = newNode;
                if (newNode.next != null) {
                    newNode.next.prev = newNode;
                }
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkCorrectIndex(index);
        return lookForNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkCorrectIndex(index);
        Node<T> node = lookForNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkCorrectIndex(index);
        Node<T> removedNode = lookForNode(index);
        if (removedNode.prev != null) {
            removedNode.prev.next = removedNode.next;
        } else {
            first = removedNode.next;
        }
        if (removedNode.next != null) {
            removedNode.next.prev = removedNode.prev;
        } else {
            last = removedNode.prev;
        }
        size--;
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if ((currentNode.item == object)
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    private Node<T> lookForNode(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkCorrectIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Wrong index - " + index + ", try again !");
        }
    }
}
