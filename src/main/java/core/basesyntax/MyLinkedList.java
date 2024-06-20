package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            addFirstNode(newNode);
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        ensureIndexWithinBounds(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            if (head == null) {
                addFirstNode(newNode);
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) {
            if (tail == null) {
                addFirstNode(newNode);
            } else {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            }
        } else {
            Node<T> nodeAtIndex = findNodeByIndex(index);
            Node<T> prevNode = nodeAtIndex.prev;
            newNode.next = nodeAtIndex;
            newNode.prev = prevNode;
            prevNode.next = newNode;
            nodeAtIndex.prev = newNode;
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
        ensureIndexWithinSize(index);
        Node<T> nodeAtIndex = findNodeByIndex(index);
        return nodeAtIndex.item;
    }

    @Override
    public T set(T value, int index) {
        ensureIndexWithinSize(index);
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                T result = current.item;
                current.item = value;
                return result;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        ensureIndexWithinSize(index);
        Node<T> toRemove;
        if (index == 0) {
            toRemove = head;
            unlink(head);
        } else if (index == size - 1) {
            toRemove = tail;
            unlink(tail);
        } else {
            toRemove = findNodeByIndex(index);
            unlink(findNodeByIndex(index));
        }

        size--;
        return toRemove.item;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.item == null)
                    || (object != null && object.equals(current.item))) {
                unlink(current);
                size--;
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

    private void ensureIndexWithinBounds(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void ensureIndexWithinSize(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There is no such index in the list, "
                    + index);
        }
    }

    private void addFirstNode(Node<T> node) {
        head = node;
        tail = node;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
