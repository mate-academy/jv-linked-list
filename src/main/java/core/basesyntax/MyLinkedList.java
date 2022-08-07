package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node newNode = new Node(value);
        if (index == 0) {
            newNode.next = head;
            newNode.prev = null;
            head.prev = newNode;
            head = newNode;
        } else {
            newNode.next = getNodeByIndex(index);
            newNode.prev = getNodeByIndex(index - 1);
            getNodeByIndex(index).prev = newNode;
            getNodeByIndex(index - 1).next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index > size / 2) {
            Node temp = tail;
            for (int i = 0; i < size - index - 1; i++) {
                temp = temp.prev;
            }
            return temp.value;
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T originValue = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return originValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T originValue = getNodeByIndex(index).value;
        unlink(getNodeByIndex(index), index);
        return originValue;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object != null && object.equals(get(i)) || get(i) == object) {
                remove(i);
                return true;
            }
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Inappropriate index: "
                    + index + " for size: " + size);
        }
    }

    private Node getNodeByIndex(int index) {
        checkIndex(index);
        if (index > size / 2) {
            Node temp = tail;
            for (int i = 0; i < size - index - 1; i++) {
                temp = temp.prev;
            }
            return temp;
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private void unlink(Node node, int index) {
        if (index == 0) {
            head = head.next;
        }
        if (index == size - 1) {
            tail = tail.prev;
        }
        if (index > 0 && index < size - 1) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        public Node(T value) {
            this.value = value;
        }
    }

    public void printDebug() {

        System.out.println("Forward:");
        Node tmp = head;
        for (int i = 0; i < size; i++) {
            System.out.println(tmp.value);
            tmp = tmp.next;
        }

        System.out.println("Backward:");
        Node tmp1 = tail;
        for (int i = 0; i < size; i++) {
            System.out.println(tmp1.value);
            tmp1 = tmp1.prev;
        }

        System.out.println("get:");
        for (int i = 0; i < size; i++) {
            System.out.println(get(i));
        }
    }
}
