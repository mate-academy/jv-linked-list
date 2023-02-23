package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
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
        indexCheck(index);
        Node<T> newNode = new Node<>(null,value, head);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
            newNode.next = current;
        }
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T temp = current.item;
        current.item = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        return unlink(index);
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (object == temp.item || object != null && object.equals(temp.item)) {
                unlink(i);
                return true;
            }
            temp = temp.next;
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

    private Node<T> getNode(int index) {
        indexCheck(index);
        Node<T> current;
        if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current;
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index
                    + " Please only positive numbers no larger than the size of the array");
        }
    }

    private T unlink(int index) {
        T temp;
        if (index == 0) {
            temp = head.item;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = getNode(index - 1);
            temp = prev.next.item;
            prev.next = prev.next.next;
            if (index == size - 1) {
                tail = prev;
            }
        }
        size--;
        return temp;
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
