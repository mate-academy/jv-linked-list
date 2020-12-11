package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        }
        if (size == 1) {
            tail = new Node<>(head, value, null);
            head.next = tail;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        Node<T> node = getNodeByIndex(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (index == 0) {
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        newNode.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element: list) {
            tail.next = new Node<>(tail, element, null);
            tail = tail.next;
        }
        size += list.size();
        return true;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldItem;
        if (isIndexValid(index)) {
            Node<T> editNode = getNodeByIndex(index);
            oldItem = editNode.item;
            editNode.item = value;
        } else {
            throw new IndexOutOfBoundsException();
        }
        return oldItem;
    }

    @Override
    public T remove(int index) {
        T value = get(index);
        if (index == 0 && size == 1) {
            head = null;
            tail = null;
            size = 0;
            return value;
        }
        Node<T> node = getNodeByIndex(index);
        if (index == 0) {
            head = null;
            head = node.next;
        } else if (index == size - 1) {
            tail = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        int index = getItemIndex(object);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
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
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < size;
    }

    private Node<T> getNodeByIndex(int index) {
        if (!isIndexValid(index)) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current;
        if (size / 2 >= index) {
            current = head;
            for (int i = 0; i != index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i != index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private int getItemIndex(T value) {
        for (int i = 0; i < size; i++) {
            if (value == getNodeByIndex(i).item || value != null
                    && value.equals(getNodeByIndex(i).item)) {
                return i;
            }
        }
        return -1;
    }
}
