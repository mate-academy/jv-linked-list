package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value,null);
            tail = head;
            size++;
            return;
        }
        Node<T> currentNode = new Node<>(tail, value, null);
        tail.next = currentNode;
        tail = currentNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid");
        } else {
            if (index == size) {
                add(value);
                return;
            }
            if (index == 0) {
                Node<T> newNode = new Node<>(null, value, head);
                head.prev = newNode;
                head = newNode;
                size++;
                return;
            }
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (indexIsValid(index)) {
            return getNodeByIndex(index).item;
        } else {
            throw new IndexOutOfBoundsException("Index is not valid");
        }
    }

    @Override
    public T set(T value, int index) {
        if (indexIsValid(index)) {
            T temp = getNodeByIndex(index).item;
            getNodeByIndex(index).item = value;
            return temp;
        } else {
            throw new IndexOutOfBoundsException("Index is not valid");
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid");
        } else {
            if (size == 1 && index == 0) {
                size--;
                Node<T> removedNode = tail;
                tail = head = null;
                return removedNode.item;
            }
            if (index == size - 1) {
                size--;
                Node<T> removedNode = tail;
                tail = tail.prev;
                tail.next.prev = tail.next = null;
                return removedNode.item;
            }
            if (index == 0) {
                size--;
                Node<T> removedNode = head;
                head = head.next;
                head.prev.next = head.prev = null;
                return removedNode.item;
            }
            Node<T> currentNode = getNodeByIndex(index);
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            size--;
            return currentNode.item;
        }
    }

    @Override
    public boolean remove(T object) {
        if (getIndexByValue(object) == -1) {
            return false;
        } else {
            remove(getIndexByValue(object));
            return true;
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private int getIndexByValue(T value) {
        Node<T> currentNode = head;
        int index = 0;
        while (!compare(currentNode.item, value)) {
            if (index == size - 1) {
                index = -1;
                break;
            }
            currentNode = currentNode.next;
            index++;
        }
        return index;
    }

    private boolean compare(T element1, T element2) {
        return element1 == element2 || element1 != null && element1.equals(element2);
    }

    private boolean indexIsValid(int index) {
        return index < size && index >= 0;
    }

    public class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
