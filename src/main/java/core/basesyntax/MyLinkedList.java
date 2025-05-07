package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value,null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
        } else {
            Node<T> currentNode = finedNodeByIndex(index);
            currentNode.prev = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.prev.next = currentNode.prev;
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
        return finedNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> curentNode = finedNodeByIndex(index);
        T result = curentNode.value;
        curentNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        T deletedNodeValue = null;
        Node<T> removedNode = finedNodeByIndex(index);
        if (removedNode != null) {
            deletedNodeValue = removedNode.value;
            unlink(removedNode);
        }
        return deletedNodeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> curentNode = finedElementByValue(object);
        if (curentNode != null) {
            unlink(curentNode);
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void unlink(Node<T> node) {
        if (head == node) {
            if (size == 1) {
                head = null;
                size--;
                return;
            }
            head = head.next;
            head.prev = null;
            size--;
        } else if (tail == node) {
            tail.prev.next = null;
            tail = tail.prev;
            size--;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
    }

    private Node<T> finedElementByValue(T object) {
        Node<T> curentNode = head;
        for (int i = 0; i < size; i++) {
            if (curentNode.value == null && object == null || curentNode.value.equals(object)) {
                return curentNode;
            }
            curentNode = curentNode.next;
        }
        return curentNode;
    }

    private Node<T> finedNodeByIndex(int index) {
        checkIndex(index);
        int seredina = (size / 2);
        if (index <= seredina) {
            Node<T> curentNode = head;
            for (int i = 0; i <= seredina; i++) {
                if (i == index) {
                    return curentNode;
                }
                curentNode = curentNode.next;
            }
            return curentNode;
        } else {
            Node<T> curentNode = tail;
            for (int i = size - 1; i > seredina; i--) {
                if (i == index) {
                    return curentNode;
                }
                curentNode = curentNode.prev;
            }
        }
        return null;
    }
}
