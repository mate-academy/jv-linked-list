package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<T>(value);
        if (size > 0) {
            addedNode.prev = tail;
            tail.next = addedNode;
            tail = addedNode;
        }
        ifEmptyList(addedNode);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexSizeMore(index);
        if (index == size) {
            add(value);
        } else {
            addIfListFilled(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node: list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue;
        Node<T> node = getNode(index);
        removedValue = node.value;
        unlink(node);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        try {
            Node<T> node = getNode(object);
            unlink(node);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void unlink(Node node) {
        if (node.equals(head) && node.equals(tail)) {
            head = null;
            tail = null;
            size--;
        } else if (node.equals(head)) {
            node.next.prev = null;
            head = node.next;
            size--;
        } else if (node.equals(tail)) {
            node.prev.next = null;
            size--;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void ifEmptyList(Node<T> node) {
        if (isEmpty()) {
            head = node;
            tail = node;
        }
    }

    private void addIfListFilled(T value, int index) {
        Node<T> addedNode = new Node<T>(value);
        if (index == 0) {
            addedNode.next = head;
            head = addedNode;
            addedNode.next.prev = addedNode;
        } else {
            Node<T> currentNode = getNode(index);
            addedNode.prev = currentNode.prev;
            addedNode.next = currentNode;
            currentNode.prev.next = addedNode;
            currentNode.prev = addedNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size());
        }
    }

    private void checkIndexSizeMore(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size());
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index <= size / 2) {
            current = head;
            int i = 0;
            while (i < index) {
                current = current.next;
                i++;
            }
        } else {
            current = tail;
            int i = size - 1;
            while (i > index) {
                current = current.prev;
                i--;
            }
        }
        return current;
    }

    private Node<T> getNode(T object) {
        Node<T> current;
        int index = 0;
        if (index <= size / 2) {
            current = head;
            while (index <= size / 2) {
                if ((current.value == null && object != null)
                        || (current.value != null && !current.value.equals(object))) {
                    current = current.next;
                } else {
                    return current;
                }
                index++;
            }
        }
        if (index > size / 2) {
            current = tail;
            index = size - 1;
            while (index >= size / 2) {
                if ((current.value == null && object != null)
                        || (current.value != null && !current.value.equals(object))) {
                    current = current.prev;
                } else {
                    return current;
                }
                index--;
            }
        }
        throw new RuntimeException(object + " not found.");
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }
}
