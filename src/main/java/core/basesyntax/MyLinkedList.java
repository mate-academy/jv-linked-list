package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("You used an incorrect index");
        }
        if (index == size) {
            addLast(value);
        } else {
            Node<T> currentNode = searchNode(index);
            Node<T> previousNode = currentNode.prev;
            Node<T> newNode = new Node<>(previousNode, value, currentNode);
            currentNode.prev = newNode;
            if (previousNode == null) {
                first = newNode;
            } else {
                previousNode.next = newNode;
            }
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
        checkIndex(index);
        return searchNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> foundNode = searchNode(index);
        T oldValue = foundNode.item;
        foundNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(searchNode(index));
    }

    @Override
    public boolean remove(T value) {
        for (Node<T> node = first; node != null; node = node.next) {
            if ((Objects.equals(node.item, value)) || (value != null && value.equals(node.item))) {
                unlink(node);
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
        return size() == 0;
    }

    private void addLast(T value) {
        Node<T> previousListElement = last;
        Node<T> newNode = new Node<>(last, value, null);
        last = newNode;
        if (previousListElement == null) {
            first = newNode;
        } else {
            previousListElement.next = newNode;
        }
    }

    private Node<T> searchNode(int index) {
        if (index > (size / 2)) {
            Node<T> soughtNode = last;
            for (int i = size - 1; i > index; i--) {
                soughtNode = soughtNode.prev;
            }
            return soughtNode;
        } else {
            Node<T> soughtNode = first;
            for (int i = 0; i < index; i++) {
                soughtNode = soughtNode.next;
            }
            return soughtNode;
        }
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("You used an incorrect index. Element not found");
        }
    }

    private T unlink(Node<T> searchNode) {
        final T element = searchNode.item;
        Node<T> next = searchNode.next;
        Node<T> previous = searchNode.prev;
        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
            searchNode.next = null;
        }
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            searchNode.prev = null;
        }
        searchNode.item = null;
        size--;
        return element;
    }
}
