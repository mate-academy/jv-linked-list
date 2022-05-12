package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node node = new Node<>(value, null, null);
        if (size == 0) {
            first = node;
        } else {
            Node currentNode = last;
            currentNode.next = node;
            node.prev = currentNode;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (size == 0 || index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node node = new Node<>(value, null, null);
            node.next = first;
            first.prev = node;
            first = node;
            size++;
            return;
        }
        Node node = new Node<>(value, null, null);
        Node currentNode = getNodeByIndex(index);
        currentNode.prev.next = node;
        node.prev = currentNode.prev;
        currentNode.prev = node;
        node.next = currentNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listItem: list) {
            add(listItem);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node currentNode = getNodeByIndex(index);
        return (T) currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node currentNode = getNodeByIndex(index);
        T oldValue = (T) currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node currentNode = getNodeByIndex(index);
        unlink(currentNode);
        size--;
        return (T) currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = first;
        int index = 0;
        while (index < size) {
            if (currentNode.item == object || currentNode.item.equals(object)) {
                remove(index);
                return true;
            }
            currentNode = currentNode.next;
            index++;
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

    private Node getNodeByIndex(int index) {
        int count = 0;
        if (index <= size / 2) {
            Node currentNode = first;
            while (count != index) {
                currentNode = currentNode.next;
                count++;
            }
            return currentNode;
        }
        if (index > size / 2) {
            Node currentNode = last;
            while (count != size - index - 1) {
                currentNode = currentNode.prev;
                count++;
            }
            return currentNode;
        }
        return null;
    }

    private void unlink(Node nodeToUnlink) {
        if (nodeToUnlink == first && nodeToUnlink == last) {
            first = null;
            last = null;
            return;
        }
        if (nodeToUnlink == first) {
            nodeToUnlink.next.prev = null;
            first = nodeToUnlink.next;
        } else if (nodeToUnlink == last) {
            nodeToUnlink.prev.next = null;
            last = nodeToUnlink.prev;
            return;
        } else {
            nodeToUnlink.prev.next = nodeToUnlink.next;
            nodeToUnlink.next.prev = nodeToUnlink.prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index == size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private static class Node<T> {

        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}

