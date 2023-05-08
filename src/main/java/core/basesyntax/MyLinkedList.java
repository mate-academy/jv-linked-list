package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node node = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node node = new Node<>(null, value, null);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
        } else {
            Node<T> current = findNodeByIndex(index);
            node.next = current;
            node.prev = current.prev;
            current.prev.next = node;
            current.prev = node;
        }
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeByIndex(index);
        T item = node.item;
        node.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNodeByIndex(index);
        remover(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = findNodeByObject(object);
        if (current == null) {
            return false;
        }
        remover(current);
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

    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> currentElement;
        if (index < size / 2) {
            currentElement = head;
            for (int i = 0; i < index; i++) {
                currentElement = currentElement.next;
            }
        } else {
            currentElement = tail;
            for (int i = size - 1; i > index; i--) {
                currentElement = currentElement.prev;
            }
        }
        return currentElement;
    }

    private Node<T> findNodeByObject(T object) {
        Node<T> currentNode = head;
        int index = 0;
        while (index != size) {
            if ((currentNode.item == object)
                    || (currentNode.item != null && currentNode.item.equals(object))) {
                return currentNode;
            }
            currentNode = currentNode.next;
            index++;
        }
        return null;
    }

    private void remover(Node<T> current) {
        if (current.equals(head)) {
            head = current.next;
        } else if (current.equals(tail)) {
            tail = current.prev;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
