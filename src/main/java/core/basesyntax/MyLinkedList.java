package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> last;
    private int size = 0;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = head;
        if (head == null) {
            head = newNode;
            last = newNode;
            size++;
        } else {
            currentNode =last;
            currentNode.next = newNode;
            newNode.prev = currentNode;
            last = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        Node<T> prevNode = null;
        Node<T> currentNode;
        if (!isEmpty() && index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        } else if (!isEmpty() && index == size()) {
            add(value);
        } else if (isEmpty() && index == 0) {
            add(value);
        } else {
            currentNode = getNode(index);
            prevNode = currentNode.prev;
            prevNode.next = newNode;
            newNode.next = currentNode;
            currentNode.prev = newNode;
            newNode.prev = prevNode;
            size++;
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
        Node<T> currentNode = getNode(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        if (isValidIndex(index)) {
            Node<T> node = getNode(index);
            T item = node.item;
            node.item = value;
            return item;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        T value = null;
        if (isValidIndex(index)) {
            value = unlink(getNode(index));
        }
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.item == object || object != null
                    && object.equals(currentNode.item)) {
                unlink(currentNode);
                return true;
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
        return size + 1;
    }

    @Override
    public boolean isEmpty() {
        return null == head;
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        if (index <= size / 2) {
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        currentNode = last;
        for (int i = size; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private boolean isValue(Node<T> node, T value) {
        return (null == value && null == node.item) || (node.item.equals(value));
    }

    private void removeNode() {
        Node<T> nextNode = null;
        Node<T> prevNode = null;
        Node<T> currentNode = null;
        nextNode = currentNode.next;
        prevNode = currentNode.prev;
        nextNode.prev = prevNode;
        prevNode.next = nextNode;
        size--;
    }

    private boolean isValidIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Not valid index");
        } else {
            return true;
        }
    }

    private void removeHeadAndLastNode() {
        head = null;
        last = null;
        size--;
    }

    private T unlink(Node<T> node) {
        final T value = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return value;
    }
}
