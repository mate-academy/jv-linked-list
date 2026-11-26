package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> firstNode = new Node<>(null, value, null);
            this.first = firstNode;
            this.last = firstNode;
            size++;
        } else if (size > 0) {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            isIndexException(index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = getNodeByIndex(index);
        Node<T> previous = current.prev;
        Node<T> newNode = new Node<>(previous, value, current);
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        int originalSize = list.size();
        for (int index = 0; index < originalSize; index++) {
            add(list.get(index));
        }
    }

    @Override
    public T get(int index) {
        isIndexException(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        isIndexException(index);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexException(index);
        Node<T> deleting = getNodeByIndex(index);
        return unlink(deleting);
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexByObject(object);
        if (index == -1) {
            return false;
        }
        unlink(getNodeByIndex(index));
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

    class Node<T> {

        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void isIndexException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", not found.");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (size / 2 > index) {
            current = first;
            for (int node = 0; node < index; node++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int node = size - 1; node > index; node--) {
                current = current.prev;
            }
        }
        return current;
    }

    private int getIndexByObject(T object) {
        Node<T> current = first;
        int index = -1;
        while (current != null) {
            index++;
            if (current.item == object || (current.item != null && object != null
                    && current.item.equals(object))) {
                return index;
            }
            current = current.next;
        }
        return -1;
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        node.prev = null;
        node.next = null;
        T removeNodeItem = node.item;
        node.item = null;
        size--;
        return removeNodeItem;
    }
}
