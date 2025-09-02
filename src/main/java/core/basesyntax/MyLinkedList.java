package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            link(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if ((index < 0) || (index > size)) {
            throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            link(null, value, head);
        } else {
            Node<T> prev = findNodeByIndex(index - 1);
            link(prev, value, prev.next);
        }
        size++;
        checkHeadAndTail();
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
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> temp = findNodeByIndex(index);
        unlink(temp);
        return temp.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = getNode(object);
        if (temp != null) {
            unlink(temp);
            return true;
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

    private Node<T> findNodeByIndex(int index) {
        int currentIndex;
        Node<T> currentNode;
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        if (index > size / 2) {
            currentNode = tail;
            currentIndex = size - 1;
            while (currentIndex != index) {
                currentNode = currentNode.prev;
                currentIndex--;
            }

        } else {
            currentNode = head;
            currentIndex = 0;
            while (currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
        }
        return currentNode;
    }

    private Node<T> getNode(T object) {
        Node<T> temp = head;
        while (temp != null && !(temp.value == null ? object == null : temp.value.equals(object))) {
            temp = temp.next;
        }
        return temp;
    }

    private void link(Node<T> prev, T value, Node<T> next) {
        Node<T> newNode = new Node<>(prev, value, next);
        if (prev != null) {
            prev.next = newNode;
        }
        if (next != null) {
            next.prev = newNode;
        }
    }

    private void checkIndex(int i) {
        if (0 > i || i >= size) {
            throw new IndexOutOfBoundsException(i + " is out of bounds for size " + size);
        }
    }

    private void unlink(Node<T> temp) {
        if (temp.prev != null) {
            temp.prev.next = temp.next;
        }
        if (temp.next != null) {
            temp.next.prev = temp.prev;
        }
        if (temp == head) {
            head = head.next;
        }
        if (temp == tail) {
            tail = tail.prev;
        }
        temp.next = null;
        temp.prev = null;
        size--;
    }

    private void checkHeadAndTail() {
        if (tail.next != null) {
            tail = tail.next;
        }

        if (head.prev != null) {
            head = head.prev;
        }
    }

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}

