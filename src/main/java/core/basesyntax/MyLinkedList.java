package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("No such index exists");
        }
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.previous = newNode;
            first = newNode;
        } else {
            Node<T> newNode = getNode(index);
            Node<T> added = new Node<>(newNode.previous, value, newNode);
            newNode.previous.next = added;
            newNode.previous = added;
            Node<T> currentNode = getNode(index);
            currentNode.previous.next = added;
            currentNode.previous = added;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNode(index);
        T currentValue = newNode.item;
        newNode.item = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentItem = first;
        for (int i = 0; i < size; i++) {
            if (object == currentItem.item || object != null && object.equals(currentItem.item)) {
                unlink(currentItem);
                return true;
            }
            currentItem = currentItem.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index exists");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current = null;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.previous;
        if (previousNode == null) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
            node.previous = null;
        }
        if (nextNode == null) {
            last = previousNode;
        } else {
            nextNode.previous = previousNode;
            node.next = null;
        }
        size--;
        return node.item;
    }
}
