package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public boolean add(T value) {
        Node node = new Node(tail, value, null);
        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (!(checkIndex(index) || index == size)) {
            throw new IndexOutOfBoundsException("Failed add to LinkedList. Please check index");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node currentNode = findIndex(index);
        Node newNode = new Node(currentNode.previous, value, currentNode);
        if (index == 0) {
            head = newNode;
        } else {
            currentNode.previous.next = newNode;
        }
        currentNode.previous = newNode;
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
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Failed get from LinkedList. Please check index");
        }
        return findIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Failed set in LinkedList. Please check index");
        }
        Node node = findIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Failed delete from LinkedList."
                    + " Please check index");
        }
        Node node = findIndex(index);
        if (node == tail && node == head) {
            head = null;
            tail = null;
        } else if (node == tail) {
            node.previous.next = null;
            tail = node.previous;
        } else if (node == head) {
            node.next.previous = null;
            head = node.next;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Cannot delete from empty list");
        }
        int index = getIndex(object);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private Node previous;
        private T value;
        private Node next;

        public Node(Node previous, T value, Node next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    private Node findIndex(int index) {
        Node node = head;
        int listIndex = 0;
        while (listIndex != index) {
            node = node.next;
            listIndex++;
        }
        return node;
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }

    private int getIndex(T value) {
        Node node = head;
        int index = 0;
        while (node != null) {
            if (node.value == value || (node.value != null && node.value.equals(value))) {
                return index;
            }
            index++;
            node = node.next;
        }
        return -1;
    }
}
