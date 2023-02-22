package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (first == null) {
            newNode = new Node<>(null, value, null);
            first = newNode;
        } else {
            newNode = new Node<>(last, value, null);
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> newNode;
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> nodeByIndex = getNodeByIndex(index);
            newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = newNode;
            nodeByIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("Could not add list, because it is null");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> deleteNode = getNodeByIndex(index);
        if (isEmpty()) {
            throw new RuntimeException("List is Empty!");
        } else if (size == 1) {
            first = null;
            last = null;
        } else if (deleteNode == first) {
            first = deleteNode.next;
            first.prev = null;
        } else if (deleteNode == last) {
            last = deleteNode.prev;
            last.next = null;
        } else {
            deleteNode.prev.next = deleteNode.next;
            deleteNode.next.prev = deleteNode.prev;
        }
        size--;
        return deleteNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.item == object
                    || (current.item != null) && (current.item).equals(object)) {
                remove(i);
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

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index, size);
        Node<T> result = first;
        if ((size / 2) > index) {
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = last;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new LinkedListIndexOutOfBoundsException("Index Out Of Bounds");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
