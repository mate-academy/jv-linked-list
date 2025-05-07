package core.basesyntax;

import java.util.List;
import org.w3c.dom.Node;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last = newNode;
        if (size == 0) {
            first = newNode;
        } else {
            last.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
        } else {
            Node<T> currentNode = findByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
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
        return (T) findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = findByIndex(index);
        T oldValue = setNode.item;
        setNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = findByIndex(index);
        unlink(removeNode);
        return removeNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            Node<T> removeNode = findByIndex(i);
            if (object == removeNode.item || object != null
                    && object.equals(removeNode.item)) {
                unlink(removeNode);
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

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " out of bounds size " + size);
        }
    }

    private Node findByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unlink(Node node) {
        if (first == node) {
            first = node.next;
        } else if (last == node) {
            last = node.prev;
            last.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
