package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> next = getNodeByIndex(index - 1);
            Node<T> prev = next.prev;
            newNode = new Node<>(prev, value, next);
            newNode.next = next.next;
            next.next = newNode;
            newNode.prev = next;
            newNode.next.prev = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNodeByIndex(index);
        T previousValue = (T) newNode.value;
        newNode.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        return unlink(index);
    }

    @Override
    public boolean remove(T object) {
        int currentIndex = 0;
        for (Node<T> node = head; node != null; node = node.next) {
            if ((object == null && node.value == null)
                    || (object != null && object.equals(node.value))) {
                unlink(currentIndex);
                return true;
            }
            currentIndex++;
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

    private void checkForValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range."
                    + " Current array size is " + size + ". Please, enter correct index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkForValidIndex(index);
        Node<T> newNode = head;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        } else {
            newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
        }
        return newNode;
    }

    private T unlink(int index) {
        Node<T> newNode = head;
        if (index == 0) {
            head = head.next;
        } else {
            checkForValidIndex(index);
            Node<T> node = getNodeByIndex(index - 1);
            newNode = node.next;
            if (index == size - 1) {
                node.next = null;
            } else {
                node.next.next.prev = node;
                node.next = node.next.next;
            }
        }
        size--;
        return (T) newNode.value;
    }

    public static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(Node prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
