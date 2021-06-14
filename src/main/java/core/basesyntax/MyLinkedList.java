package core.basesyntax;

import java.util.List;

public class MyLinkedList<V> implements MyLinkedListInterface<V> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(V value) {
        if (size == 0) {
            head = new Node(null, tail, value);
            size++;
            return;
        }
        if (size == 1) {
            tail = new Node(head, null, value);
            head.next = tail;
            size++;
            return;
        }
        Node newNode = new Node(tail.prev, tail, tail.value);
        (tail.prev).next = newNode;
        tail.prev = newNode;
        newNode.next = tail;
        tail.value = value;
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
            node = new Node(head, head.next, head.value);
            (head.next).prev = node;
            head.next = node;
            head.value = value;
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
        Node next = head;
        int counter = 0;
        while (counter < index) {
            next = next.next;
            counter++;
        }
        return next.value;
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
        Node go = head;
        int counter = 0;
        while (!(go == null)) {
            if (go.value == object || (go.value != null && go.value.equals(object))) {
                V object2 = remove(counter);
                return (object2 == object || (object2 != null && object2.equals(object)));
            }
            counter++;
            go = go.next;
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

    private Node getByIndex(int index) {
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        if (size % index == 0 || index < size / 2) {
            Node next = head;
            for (int t = 1; t <= index; t++) {
                next = next.next;
                if (t == index) {
                    return next;
                }
            }
        } else {
            Node prev = tail;
            for (int t = size - 2; t >= index; size--) {
                prev = prev.prev;
                if (t == index) {
                    return prev;
                }
            }
        }
        return null;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
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
