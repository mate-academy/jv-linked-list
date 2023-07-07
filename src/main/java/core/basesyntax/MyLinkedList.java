package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> head;
    Node<T> tail;
    int size;

    @Override
    public void add(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(value, last, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNodeTop = new Node<>(value, null, head);
            head = newNodeTop;
            size++;
            return;
        }
        rangeCheckForAdd(index);
        Node<T> currentNode = head;
        int count = 0;

        while (count != index) {
            count++;
            currentNode = currentNode.next;
        }

        Node<T> newNode = new Node<>(value, currentNode.prev, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
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
        rangeCheckForAdd(index);
        Node<T> currentNode = head;
        int count = 0;
        while (count != index) {
            count++;
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        rangeCheckForAdd(index);
        Node<T> currentNode = head;
        int count = 0;
        while (count != index) {
            count++;
            currentNode = currentNode.next;
        }
        T element = currentNode.value;
        currentNode.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        rangeCheckForAdd(index);
        Node<T> currentNode = head;
        int count = 0;
        while (count != index) {
            count++;
            currentNode = currentNode.next;
        }
        T element = currentNode.value;
        unlink(currentNode);

        return element;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.value)) {
                    unlink(x);
                    return true;
                }
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

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index == size || index > size) {
            throw new IndexOutOfBoundsException("Wrong index" + index + "! "
                    + "Index value must be less than size and greater -1");
        }
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode.prev == null) {
            head = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
        }

        if (currentNode.next == null) {
            tail = currentNode.prev;
        } else {
            currentNode.next.prev = currentNode.prev;
        }
        currentNode.next = null;
        currentNode.prev = null;
        currentNode.value = null;
        size--;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
