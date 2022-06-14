package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        first = null;
        last = null;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (first == null) {
            first = newNode;
        } else {
            newNode.previous = last;
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            first.previous = new Node<>(value, null, first);
            first = first.previous;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(value, currentNode.previous, currentNode);
            currentNode.previous = newNode;
            newNode.previous.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToDelete = getNodeByIndex(index);
        unlink(nodeToDelete);
        return nodeToDelete.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToDelete = getNodeByValue(object);
        if (nodeToDelete == null) {
            return false;
        } else {
            unlink(nodeToDelete);
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    public static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index != 0 && index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("Incorrect index! Index: %s, Size: %s", index, size));
        }
        int count = 0;
        Node<T> currentNode = first;
        while (count != index) {
            currentNode = currentNode.next;
            count++;
        }
        return currentNode;
    }

    private Node<T> getNodeByValue(T value) {
        if (size == 1) {
            return first;
        }
        Node<T> currentNode = first;
        while (currentNode != last) {
            if (currentNode.value == value
                    || (currentNode.value != null && currentNode.value.equals(value))) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = null;
            last = null;
        } else if (node == first) {
            first.next.previous = null;
            first = first.next;
        } else if (node == last) {
            last.previous.next = null;
            last = last.previous;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
    }

}
