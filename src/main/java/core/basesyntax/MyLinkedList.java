package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> prev = tail;
        Node<T> newNode = new Node<T>(prev, value, null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index == 0) {
            head = new Node<>(null, value, head);
            if (size == 0) {
                tail = head;
            }
            size++;
            return;
        }
        Node<T> newNode = new Node(tail, value, null);
        if (index == size) {
            tail.next = newNode;
            tail = newNode;
        }
        Node<T> oldNode = head;
        for (int i = 0; i < index; i++) {
            oldNode = oldNode.next;
        }
        Node oldPrevious = oldNode.prev;
        oldPrevious.next = newNode;
        oldNode.prev = newNode;
        newNode.prev = oldPrevious;
        newNode.next = oldNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        isIndexExist(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        isIndexExist(index);
        Node<T> node = findNodeByIndex(index);
        T oldNode = node.value;
        node.value = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        isIndexExist(index);
        Node<T> removedNode = findNodeByIndex(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (objEquals(current.value, object)) {
                unlink(current);
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
        return (size == 0);
    }

    private boolean objEquals(T value1, T value2) {
        if (value1 == null) {
            return value2 == null;
        }
        return value1.equals(value2);
    }

    private void isIndexExist(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " does not exist");
        }
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> findNodeByIndex(int index) {
        isIndexExist(index);
        Node<T> temp;
        if (index < size / 2) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        if (temp == null) {
            throw new IndexOutOfBoundsException("Index error");
        }
        return temp;
    }
}
