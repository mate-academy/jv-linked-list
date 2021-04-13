package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

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

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            last = newNode;
            first = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
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
        }
         else {
            addToMiddle(value,index);
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T elements : list) {
            add(elements);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkRange(index);
        Node<T> itemNode;
        if (size/2 <= index) {
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
        if (index == 0) {
            newNode = first;
            deletedItem = newNode.item;
            first = first.next;
            size--;
            return deletedItem;
        }
        if (index == size - 1) {
            newNode = last;
            deletedItem = newNode.item;
            last = last.prev;
        } else {
            newNode = getNodeByIndexFromFirst(index);
            deletedItem = newNode.item;
            newNode.prev.next = newNode.next;
            newNode.next.prev = newNode.prev;
        }
        size--;
        return deletedItem;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((get(i) == object || (get(i) != null && get(i).equals(object)))) {
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
        return  itemByIndex;
    }

    private void addToMiddle(T value, int index) {
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<T>(node.prev, value, node);
        node.prev.next = newNode;
        node.prev = newNode;
    }

    private Node<T> getNode(int index) {
        checkRange(index);
        if (size/2 < index) {
            return getNodeByIndexFromLast(index);
        }
        return getNodeByIndexFromFirst(index);
    }

    private void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index"
                    + index + " out of bounds exception");
        }
    }
}
