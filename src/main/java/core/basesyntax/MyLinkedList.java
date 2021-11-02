package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    private static class Node<T> {
        private T item;
        private MyLinkedList.Node<T> next;
        private MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> item = new Node<>(null, value, null);
            head = item;
            tail = item;
        } else {
            Node<T> item = new Node<T>(tail, value, null);
            tail.next = item;
            tail = item;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> second = head;
            head = new Node<>(null, value, second);
            second.prev = head;
            size++;
            return;
        }
        Node<T> x = getNode(index);
        Node<T> newItem = new Node<>(null, value, null);
        newItem.next = x;
        newItem.prev = x.prev;
        x.prev.next = newItem;
        x.prev = newItem;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        inSize(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> x = getNode(index);
        T result = x.item;
        x.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
        if (size == 1) {
            T removed = head.item;
            head = null;
            tail = null;
            return removed;
        }
        if (index == 0) {
            head = head.next;
            size--;
            return head.prev.item;
        } else if (index == size - 1) {
            tail = tail.prev;
            size--;
            return tail.next.item;
        }
        Node<T> x = getNode(index);
        return unlink(x);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object
                    || currentNode.item != null
                    && currentNode.item.equals(object)) {
                unlink(currentNode);
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
        return head == null;
    }

    private Node<T> getNode(int index) {
        inSize(index);
        int indexLinked = 0;
        Node<T> x = head;
        while (indexLinked != index) {
            x = x.next;
            indexLinked++;
        }
        return x;
    }

    private void inSize(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
    }

    private T unlink(Node<T> currentNode) {
        Node<T> nextNode = currentNode.next;
        Node<T> prevNode = currentNode.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            currentNode.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            currentNode.next = null;
        }
        T element = currentNode.item;
        currentNode.item = null;
        size--;
        return element;
    }
}
