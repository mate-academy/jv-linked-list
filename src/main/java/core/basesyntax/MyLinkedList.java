package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node createdNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = createdNode;
        } else {
            tail.next = createdNode;
        }
        tail = createdNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index doesn't correct" + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node newAddNode = new Node<>(null, value, head);
            head.prev = newAddNode;
            head = newAddNode;
        } else {
            Node<T> fromNode = getNodeFromIndex(index);
            Node<T> node = new Node<>(fromNode.prev, value, fromNode);
            fromNode.prev.next = node;
            fromNode.prev = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T addAll : list) {
            add(addAll);
        }
    }

    @Override
    public T get(int index) {
        checkedIndex(index);
        return getNodeFromIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkedIndex(index);
        Node<T> setNode = getNodeFromIndex(index);
        T newNode = setNode.item;
        setNode.item = value;
        return newNode;
    }

    @Override
    public T remove(int index) {
        checkedIndex(index);
        return unlink(getNodeFromIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = head;
        for (int i = 0; i < size; i++) {
            if (removeNode.item == object || object != null && removeNode.item.equals(object)) {
                unlink(removeNode);
                return true;
            }
            removeNode = removeNode.next;
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

    private Node<T> getNodeFromIndex(int index) {
        checkedIndex(index);
        Node<T> getNode;
        if (index < size << 1) {
            getNode = head;
            for (int i = 0; i < index; i++) {
                getNode = getNode.next;
            }
        } else {
            getNode = tail;
            for (int i = size - 1; i > index; i--) {
                getNode = getNode.prev;
            }
        }
        return getNode;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private void checkedIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index doesn't correct" + index);
        }
    }
}
