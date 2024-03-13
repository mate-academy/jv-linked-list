package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> firstnode = tail;
        final Node<T> newNode = new Node<>(firstnode, value, null);
        tail = newNode;
        if (firstnode == null) {
            head = newNode;
        } else {
            firstnode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "this index doesn't exists in linked list: " + index);
        }
        if (size == index) {
            add(value);
        } else {
            Node<T> nextNode = node(index);
            Node<T> previousNode = nextNode.prev;
            Node<T> newNode = new Node<>(previousNode, value, nextNode);
            if (previousNode == null) {
                head = newNode;
            } else {
                previousNode.next = newNode;
            }
            if (nextNode == null) {
                tail = newNode;
            } else {
                nextNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elements : list) {
            add(elements);
        }
    }

    @Override
    public T get(int index) {
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeOnIndex = node(index);
        T oldvalue = nodeOnIndex.item;
        nodeOnIndex.item = value;
        return oldvalue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeOnIndex = node(index);
        T removedValue = nodeOnIndex.item;
        unlinkNode(nodeOnIndex);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        int i = 0;
        while (temp != null) {
            if (temp.item == null ? object == null : temp.item.equals(object)) {
                remove(i);
                return true;
            }
            temp = temp.next;
            i++;
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
            throw new IndexOutOfBoundsException(
                    "this index doesn't exists in linked list: " + index);
        }
    }

    private Node<T> node(int index) {
        checkIndex(index);
        Node<T> x;
        if (index < (size >> 1)) {
            x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void unlinkNode(Node<T> nodeOnIndex) {
        Node<T> prevNode = nodeOnIndex.prev;
        Node<T> nextNode = nodeOnIndex.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            nodeOnIndex.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            nodeOnIndex.next = null;
        }
        nodeOnIndex.item = null;
        size--;
    }

}
