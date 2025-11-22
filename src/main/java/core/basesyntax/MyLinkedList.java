package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int arrSize = 0;

    @Override
    public void add(T value) {
        Node<T> node = new Node(value);
        if (arrSize == 0) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        arrSize += 1;
    }

    @Override
    public void add(T value, int index) {
        checkBoundries(index, arrSize);
        if (arrSize == index) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(null, value, head);
            arrSize += 1;
        } else {
            Node<T> oldNode = findNode(index);
            Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
            if (oldNode.prev != null) {
                oldNode.prev.next = newNode;
            }
            if (oldNode.next != null) {
                newNode.next.prev = newNode;
            }
            arrSize += 1;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T part : list) {
            add(part);
        }
    }

    @Override
    public T get(int index) {
        checkBoundries(index, arrSize - 1);
        Node<T> oldNode = findNode(index);
        return oldNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkBoundries(index, arrSize - 1);
        Node<T> oldNode = findNode(index);
        T old = oldNode.item;
        oldNode.item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkBoundries(index, arrSize - 1);
        Node<T> oldNode = findNode(index);
        if (arrSize == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head = head.next;
            head.prev = null;
        } else if (index == arrSize - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            if (oldNode.prev != null) {
                oldNode.prev.next = oldNode.next;
            }
            if (oldNode.next != null) {
                oldNode.next.prev = oldNode.prev;
            }
        }
        arrSize -= 1;
        return oldNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> oldNode = head;
        for (int i = 0; i < arrSize; i++) {
            if (oldNode.item != null && oldNode.item.equals(object) || oldNode.item == object) {
                remove(i);
                return true;
            }
            oldNode = oldNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return arrSize;
    }

    @Override
    public boolean isEmpty() {
        return arrSize == 0;
    }

    public void checkBoundries(int index, int bound) throws IndexOutOfBoundsException {
        if (index < 0 || index > bound) {
            throw new ArrayIndexOutOfBoundsException("Index is out of boundries");
        }
    }

    private Node findNode(int index) {
        Node<T> oldNode = head;
        for (int i = 0; i < index; i++) {
            oldNode = oldNode.next;
        }
        return oldNode;
    }

    class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T item;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }

        public Node(T item) {
            this.prev = null;
            this.next = null;
            this.item = item;
        }
    }
}
