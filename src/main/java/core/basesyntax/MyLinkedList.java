package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int quantity = 0;
    private Node<T> node;

    @Override
    public void add(T value) {
        rewindNodeToHead();
        if (quantity == 0) {
            node = new Node(null, value, null, true, true);
        } else {
            rewindToLastNode();
            node.tail = false;
            node.next = new Node<>(node, value, null, false, true);
        }
        quantity += 1;
    }

    @Override
    public void add(T value, int index) {
        if (index > quantity || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        rewindNodeToHead();
        if (index == quantity) {
            add(value);
        } else {
            rewindToSelectedNode(index);
            insertNodeInCurrentPosition(index, value);
            quantity += 1;
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
        if (index >= quantity || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        Node<T> temp = node;
        rewindNodeToHead();
        for (int i = 0; i < quantity; i++) {
            if (i != index) {
                node = node.next;
                continue;
            }
            temp = node;
        }
        return temp.element;
    }

    @Override
    public T set(T value, int index) {
        if (index >= quantity || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        rewindNodeToHead();
        rewindToSelectedNode(index);
        T res = node.element;
        node.element = value;
        return res;
    }

    @Override
    public T remove(int index) {
        if (index >= quantity || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        rewindNodeToHead();
        rewindToSelectedNode(index);
        T value = node.element;
        chooseConditionOfRemoving(index);
        quantity -= 1;
        return value;
    }

    @Override
    public boolean remove(T object) {
        rewindNodeToHead();
        int index = 0;
        while (node != null) {
            if (node.element == object || node.element != null
                    && node.element.equals(object)) {
                chooseConditionOfRemoving(index);
                quantity -= 1;
                return true;
            }
            node = node.next;
            index += 1;
        }
        return false;
    }

    @Override
    public int size() {
        return quantity;
    }

    @Override
    public boolean isEmpty() {
        return quantity == 0;
    }

    private void rewindNodeToHead() {
        if (node != null) {
            while (node.prev != null) {
                node = node.prev;
            }
        }
    }

    private void chooseConditionOfRemoving(int index) {
        if (quantity == 1) {
            node = null;
        } else if (index == 0) {
            node = node.next;
            node.prev = null;
        } else if (index == quantity - 1) {
            node = node.prev;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private void rewindToSelectedNode(int index) {
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
    }

    private void rewindToLastNode() {
        while (node.next != null) {
            node = node.next;
        }
    }

    private void insertNodeInCurrentPosition(int index, T value) {
        if (index == 0) {
            node.head = false;
            node = new Node<>(null, value, node, true,false);
            node.next.prev = node;
        } else {
            node = new Node<>(node.prev, value, node, false, false);
            node.next.prev = node;
            node.prev.next = node;
        }
    }

    class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;
        private boolean head;
        private boolean tail;

        public Node(Node prev, T element, Node next, boolean head, boolean tail) {
            this.prev = prev;
            this.element = element;
            this.next = next;
            this.head = head;
            this.tail = tail;
        }
    }

}
