package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int nodeIndex = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> target = head;
        int counter = 0;
        while (counter < index) {
            target = target.next;
            counter++;
        }
        return target;
    }

    private void unlink(Node<T> node) {
        node.next = null;
        node.prev = null;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
        } else if (tail == null) {
            tail = new Node<>(head, value, null);
            head.next = tail;
        } else {
            Node<T> element = new Node<>(tail, value, null);
            tail.next = element;
            tail = element;
        }
        nodeIndex++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > nodeIndex) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0 && nodeIndex == 0 || index == nodeIndex) {
            add(value);
        } else if (index == 0) {
            head = new Node<>(null, value, getNode(index));
            getNode(index).prev = head;
            nodeIndex++;
        } else {
            Node<T> previous = getNode(index).prev;
            Node<T> newNode = new Node<>(previous, value, getNode(index));
            previous.next = newNode;
            getNode(index).prev = newNode;
            nodeIndex++;
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
        if (index < 0 || index >= nodeIndex) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= nodeIndex) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> removedNode = getNode(index);
        if (index == nodeIndex - 1) {
            Node<T> prevNode = tail.prev;
            tail = new Node<>(prevNode, value, null);
            prevNode.next = tail;
        } else if (index == 0) {
            Node<T> nextNode = head.next;
            head = new Node<T>(null, value, nextNode);
            nextNode.prev = head;
        } else {
            Node<T> prevNode = getNode(index).prev;
            Node<T> nextNode = getNode(index).next;
            Node<T> element = new Node<>(prevNode, value, nextNode);
            prevNode.next = element;
            nextNode.prev = element;
        }
        return removedNode.item;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= nodeIndex) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> removedNote = getNode(index);
        if (index == 0) {
            head = head.next;
        } else if (index == nodeIndex - 1) {
            tail = tail.prev;
        } else {
            Node<T> previousNode = getNode(index).prev;
            Node<T> nextNode = getNode(index).next;
            unlink(getNode(index));
            previousNode.next = nextNode;
            nextNode.prev = previousNode;
        }
        nodeIndex--;
        return removedNote.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> target = head;
        int counter = 0;
        while (counter < nodeIndex) {
            if (target.item == object || target.item != null && target.item.equals(object)) {
                remove(counter);
                return true;
            }
            target = target.next;
            counter++;
        }
        return false;
    }

    @Override
    public int size() {
        return nodeIndex;
    }

    @Override
    public boolean isEmpty() {
        return nodeIndex == 0;
    }
}
