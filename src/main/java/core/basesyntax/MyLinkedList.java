package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;

        }
        last = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index == size) {
            add(value);
            return;
        }
        checkRange(index);
        if (index == 0) {
            newNode = new Node<>(null, value, first);
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            addToMiddle(value,index);
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkRange(index);
        Node<T> itemNode;
        if (size / 2 <= index) {
            itemNode = getNodeByIndexFromLast(index);
            return itemNode.item;
        } else {
            itemNode = getNodeByIndexFromFirst(index);
        }
        return itemNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkRange(index);
        Node<T> current = getNodeByIndexFromFirst(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        T deletedItem;
        Node<T> newNode;
        if (index == size - 1) {
            newNode = last;
            deletedItem = newNode.item;
            last = last.prev;
        } else if (index == 0) {
            newNode = first;
            deletedItem = newNode.item;
            first = first.next;
        } else {
            newNode = getNode(index);
            deletedItem = newNode.item;
            newNode.prev.next = newNode.next;
            newNode.next.prev = newNode.prev;
        }
        size--;
        return deletedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if ((current.item == object || current.item != null && current.item.equals(object))) {
                unlinkNode(current);
                return true;
            }
            current = current.next;
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

    private Node<T> getNodeByIndexFromFirst(int index) {
        checkRange(index);
        Node<T> itemByIndex = first;
        for (int i = 0; i < index; i++) {
            itemByIndex = itemByIndex.next;
        }
        return itemByIndex;
    }

    private Node<T> getNodeByIndexFromLast(int index) {
        checkRange(index);
        Node<T> itemByIndex = last;
        for (int i = size - 1; i > index; i--) {
            itemByIndex = itemByIndex.prev;
        }
        return itemByIndex;
    }

    private void addToMiddle(T value, int index) {
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
    }

    private Node<T> getNode(int index) {
        checkRange(index);
        if (size / 2 <= index) {
            return getNodeByIndexFromLast(index);
        }
        return getNodeByIndexFromFirst(index);
    }

    private Node<T> unlinkNode(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (previousNode != null) {
            previousNode.next = nextNode;
        } else {
            first = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = previousNode;

        } else {
            last = previousNode;
        }
        size--;
        return node;
    }

    private void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index"
                    + index + " out of bounds exception");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

}
