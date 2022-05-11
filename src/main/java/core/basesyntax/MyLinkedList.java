package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

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

    private void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkIndexSet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkIndexRemove(int index) {
        if (index < 0 || index == size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node iterateMyLinkedList(int index) {
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
            return;
        }
        if (nodeToUnlink == last) {
            nodeToUnlink.prev.next = null;
            last = nodeToUnlink.prev;
            return;
        }
        nodeToUnlink.prev.next = nodeToUnlink.next;
        nodeToUnlink.next.prev = nodeToUnlink.prev;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node node = new Node<>(value, null, null);
            first = node;
            last = node;
        }
        if (size > 0) {
            Node node = new Node<>(value, null, null);
            Node currentNode = last;
            currentNode.next = node;
            node.prev = currentNode;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (size == 0 || index == size) {
            this.add(value);
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
        Node currentNode = iterateMyLinkedList(index);
        currentNode.prev.next = node;
        node.prev = currentNode.prev;
        currentNode.prev = node;
        node.next = currentNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listItem: list) {
            this.add(listItem);
        }
    }

    @Override
    public T get(int index) {
        checkIndexSet(index);
        Node currentNode = iterateMyLinkedList(index);
        return (T) currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexSet(index);
        Node currentNode = iterateMyLinkedList(index);
        Node returnNode = new Node(currentNode.item, currentNode.next, currentNode.prev);
        currentNode.item = value;
        return (T) returnNode.item;
    }

    @Override
    public T remove(int index) {
        checkIndexRemove(index);
        Node currentNode = iterateMyLinkedList(index);
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
}
