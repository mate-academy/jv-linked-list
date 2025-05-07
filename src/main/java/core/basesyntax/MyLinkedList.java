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
        if (head == null) {
            head = newNode;
            last = newNode;
            size++;
        } else {
            Node<T> currentNode = last;
            currentNode.next = newNode;
            newNode.prev = currentNode;
            last = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            isValidIndex(index);
            Node<T> newNode = new Node<>(value);
            Node<T> currentNode = getNode(index);
            if (currentNode == head) {
                newNode.next = currentNode;
                currentNode.prev = newNode;
                head = newNode;
                size++;
            } else {
                Node<T> prevNode = currentNode.prev;
                newNode.next = currentNode;
                newNode.prev = prevNode;
                prevNode.next = newNode;
                currentNode.prev = newNode;
                size++;
            }
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
        isValidIndex(index);
        Node<T> currentNode = getNode(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        isValidIndex(index);
        Node<T> node = getNode(index);
        T item = node.item;
        node.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        isValidIndex(index);
        return unlink(getNode(index));
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
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void isValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Not valid index");
        }
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
