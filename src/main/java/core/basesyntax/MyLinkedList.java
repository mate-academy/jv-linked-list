package core.basesyntax;

import java.util.List;

public class MyLinkedList<V> implements MyLinkedListInterface<V> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(V value) {
        if (size == 0) {
            head = new Node(null, null, value);
            tail = head;
            size++;
            return;
        }
        Node newNode = new Node(tail, null, value);
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
        Node node = new Node(null, null, value);
        if (index == 0) {
            node = new Node(null, head, value);
            head.prev = node;
            head = node;
            size++;
            return;
        }
        Node node2 = getByIndex(index);
        node.next = node2;
        node.prev = node2.prev;
        node2.prev = node;
        (node.prev).next = node;
        if (index == size - 1) {
            tail = node2;
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
        if (index == size - 1) {
            return tail.value;
        }
        return getByIndex(index).value;
    }

    @Override
    public V set(V value, int index) {
        checkIndex(index);
        Node node = getByIndex(index);
        V currentValue = node.value;
        node.value = value;
        return currentValue;
    }

    @Override
    public V remove(int index) {
        checkIndex(index);
        V toReturn;
        if (size == 1) {
            size = 0;
            return head.value;
        }
        if (index == 0) {
            (head.next).prev = null;
            toReturn = head.value;
            head = head.next;
            size--;
            return toReturn;
        }
        if (index == size - 1) {
            (tail.prev).next = null;
            toReturn = tail.value;
            tail = tail.prev;
            size--;
            return toReturn;
        }
        Node toRemove = getByIndex(index);
        (toRemove.prev).next = toRemove.next;
        (toRemove.next).prev = toRemove.prev;
        size--;
        return toRemove.value;
    }

    @Override
    public boolean remove(V object) {
        int index = getIndex(object);
        if (index >= 0) {
            remove(index);
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

    public Node getByIndex(int index) {
        if (index < size / 2) {
            Node currentNode = head;
            for (int t = 0; t < index; t++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node prev = tail;
            for (int t = size - 1; t > index; t--) {
                prev = prev.prev;
            }
            return prev;
        }
    }

    public int getIndex(V value) {
        int counter = 0;
        Node runner = head;
        while (!(runner == null)) {
            if (equalValue(value, runner.value)) {
                return counter;
            }
            counter++;
            runner = runner.next;
        }
        return -1;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean equalValue(V value, V value2) {
        return ((value == value2) || (value != null && value.equals(value2)));
    }

    private class Node {
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
