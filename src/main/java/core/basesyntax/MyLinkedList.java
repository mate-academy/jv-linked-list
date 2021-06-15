package core.basesyntax;

import java.util.List;

public class MyLinkedList<V> implements MyLinkedListInterface<V> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(V value) {
        if (size == 0) {
            head = new Node<V>(null, null, value);
            tail = head;
            size++;
            return;
        }
        Node<V> newNode = new Node<>(tail, null, value);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(V value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<V> node = new Node<>(null, head, value);
            head.prev = node;
            head = node;
            size++;
            return;
        }
        Node<V> currentNode = getByIndex(index);
        Node<V> node = new Node<>(currentNode.prev, currentNode, value);
        currentNode.prev = node;
        (node.prev).next = node;
        if (index == size - 1) {
            tail = currentNode;
        }
        size++;
    }

    @Override
    public void addAll(List<V> list) {
        for (int j = 0; j < list.size(); j++) {
            add(list.get(j));
        }
    }

    @Override
    public V get(int index) {
        checkIndex(index);
        return getByIndex(index).value;
    }

    @Override
    public V set(V value, int index) {
        checkIndex(index);
        Node<V> node = getByIndex(index);
        V currentValue = node.value;
        node.value = value;
        return currentValue;
    }

    @Override
    public V remove(int index) {
        checkIndex(index);
        return unlink(getByIndex(index));
    }

    @Override
    public boolean remove(V object) {
        Node<V> node = getNode(object);
        if (node != null) {
            unlink(node);
            return true;
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

    public Node<V> getByIndex(int index) {
        if (index < size / 2) {
            Node<V> currentNode = head;
            for (int t = 0; t < index; t++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<V> prev = tail;
            for (int t = size - 1; t > index; t--) {
                prev = prev.prev;
            }
            return prev;
        }
    }

    public Node<V> getNode(V value) {
        Node<V> runner = head;
        while (!(runner == null)) {
            if (equalValue(value, runner.value)) {
                return runner;
            }
            runner = runner.next;
        }
        return null;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean equalValue(V value, V value2) {
        return ((value == value2) || (value != null && value.equals(value2)));
    }

    private V unlink(Node<V> toRemove) {
        V toReturn = toRemove.value;
        if (size == 1) {
            size = 0;
            return toReturn;
        }
        if (toRemove.prev == null) {
            (head.next).prev = null;
            head = head.next;
            size--;
            return toReturn;
        }
        if (toRemove.next == null) {
            (tail.prev).next = null;
            tail = tail.prev;
            size--;
            return toReturn;
        }
        (toRemove.prev).next = toRemove.next;
        (toRemove.next).prev = toRemove.prev;
        size--;
        return toReturn;
    }

    private static class Node<V> {
        private Node prev;
        private Node next;
        private V value;

        private Node(Node prev, Node next, V value) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
