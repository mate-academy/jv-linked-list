package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null,value,null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null,value,null);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("size = " + size + " Index= " + index);
        } else if (index > 0 && index < size) {
            Node<T> nodeTemp = getNodeByIndex(index - 1);
            newNode.next = nodeTemp.next;
            nodeTemp.next = newNode;
        } else if (index == 0 && size != 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            add(value);
            return;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index == 0) {
            return head.value;
        }
        Node<T> newNode = getNodeByIndex(index);
        return newNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        if (index == size) {
            T temp = tail.value;
            tail.value = value;
            return temp;
        }
        Node<T> nodeTemp = getNodeByIndex(index);
        T temp = nodeTemp.value;
        nodeTemp.value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue = null;
        if (head == null) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            removedValue = head.value;
            head = head.next;
            if (head != null && head.next != null) {
                head.next.prev = null;
            }
            size--;
            return removedValue;
        }
        Node<T> nodeTemp = getNodeByIndex(index - 1);
        if (nodeTemp.next != null) {
            removedValue = nodeTemp.next.value;
            nodeTemp.next = nodeTemp.next.next;
            if (nodeTemp.next != null && nodeTemp.next.next != null) {
                nodeTemp.next.next.prev = nodeTemp;
            }
            if (index == size - 1) {
                tail = nodeTemp;
            }
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeTemp = head;
        for (int i = 0; i < size; i++) {
            if (nodeTemp.value == object || (nodeTemp.value != null
                    && nodeTemp.value.equals(object))) {
                remove(i);
                return true;
            }
            nodeTemp = nodeTemp.next;
        }
        return false;
        //throw new NoSuchElementException("Object " + object + " doesn't exist.");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> nodeTemp = head;
        for (int i = 0; i < index; i++) {
            nodeTemp = nodeTemp.next;
        }
        return nodeTemp;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index = " + index
                    + ". Index can't be negative");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Element with index " + index
                    + " doesn't exist. Size of array is only " + size);
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
