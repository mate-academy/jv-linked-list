package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T item;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            Node<T> tempNode = new Node<>(last, value, null);
            last.next = tempNode;
            last = tempNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        Node<T> node = getNode(index);
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        prevNode.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T tempItem = node.item;
        node.item = value;
        return tempItem;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T tempItem = node.item;
        unlinkNode(node);
        return tempItem;
    }

    @Override
    public boolean remove(T object) {
        Node node = first;
        for (int i = 0; i < size; i++) {
            if ((node.item != null && node.item.equals(object)) || node.item == object) {
                unlinkNode(node);
                return true;
            }
            node = node.next;
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

    private void addFirst(T element) {
        first.prev = new Node<>(null, element, first);
        first = first.prev;
        size++;
    }

    private void unlinkNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node.equals(first)) {
            first = node.next;
        } else if (node.equals(last)) {
            last = node.prev;
        }
        node = null;
        size--;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
        }
        Node<T> tempNode;
        if (index <= size / 2) {
            tempNode = first;
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
        } else {
            tempNode = last;
            for (int i = size - 1; i > index; i--) {
                tempNode = tempNode.prev;
            }
        }
        return tempNode;
    }
}
