package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> last;
    private Node<T> currentNode;
    private Node<T> nextNode = null;
    private Node<T> prevNode = null;
    private int index;

    public MyLinkedList() {
        head = null;
        last = null;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            next = null;
            this.item = item;
            prev = null;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        currentNode = head;
        if (head == null) {
            index = 0;
            head = newNode;
            last = newNode;
        } else {
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
            newNode.prev = currentNode;
            last = newNode;
            index++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == 0 && isEmpty()) {
            add(value);
        }
        currentNode = isHeadOrLast(index);
        if (index == 0 && isValidIndex(index)) {
            currentNode.prev = newNode;
            newNode.next = currentNode;
            head = newNode;
            this.index++;
        } else if (index == this.index) {
            add(value);
        } else {
            currentNode = getNode(index);
            prevNode = currentNode.prev;
            prevNode.next = newNode;
            newNode.next = currentNode;
            currentNode.prev = newNode;
            newNode.prev = prevNode;
            this.index++;
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
        currentNode = head;
        if (index == 0 && isValidIndex(index)) {
            return currentNode.item;
        } else if (index == this.index) {
            currentNode = last;
            return currentNode.item;
        }
        currentNode = getNode(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        T item;
        if (index == 0 && isValidIndex(index)) {
            item = head.item;
            head.item = value;
        } else if (index == this.index) {
            item = last.item;
            last.item = value;
        } else {
            currentNode = getNode(index);
            item = currentNode.item;
            currentNode.item = value;
        }
        return item;
    }

    @Override
    public T remove(int index) {
        if (index == 0 && isValidIndex(index)) {
            head = head.next;
            head.prev = null;
            currentNode = head;
            this.index--;
        } else if (index == this.index) {
            last = last.prev;
            last.next = null;
            currentNode = last;
            this.index--;
        } else {
            currentNode = getNode(index);
            removeNode();
        }
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        if (isValue(last, object)) {
            prevNode = currentNode.prev;
            prevNode.next = null;
            last = prevNode;
            index--;
            return true;
        }
        if (isValue(head, object)) {
            nextNode = currentNode.next;
            nextNode.prev = null;
            head = nextNode;
            index--;
            return true;
        }
        currentNode = head;
        while (currentNode.next != null) {
            if (isValue(currentNode, object)) {
                removeNode();
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return index + 1;
    }

    @Override
    public boolean isEmpty() {
        return null == head ? true : false;
    }

    private Node<T> isHeadOrLast(int index) {
        return index <= (this.index / 2) ? head : last;
    }

    private Node<T> getNode(int index) {
        currentNode = isHeadOrLast(index);
        if (currentNode == head) {
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        for (int i = this.index; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private boolean isValue(Node<T> node, T value) {
        return (null == value && null == node.item) || (node.item.equals(value));
    }

    private void removeNode() {
        nextNode = currentNode.next;
        prevNode = currentNode.prev;
        nextNode.prev = prevNode;
        prevNode.next = nextNode;
        index--;
    }

    private boolean isValidIndex(int index) {
        if ((index < 0) || ((isEmpty() && index > 0) || index > this.index)) {
            throw new IndexOutOfBoundsException("Not valid index");
        } else {
            return true;
        }
    }
}
