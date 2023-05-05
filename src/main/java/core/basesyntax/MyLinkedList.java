package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(head, value);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.next = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index,size + 1);
        Node<T> nodeForeAdd = new Node<>(value);
        if (tail == null) {
            tail = head = nodeForeAdd;
        } else if (index == 0) {
            nodeForeAdd.next = tail;
            tail = nodeForeAdd;

        } else {
            insert(value,index);
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
        Objects.checkIndex(index,size);
        Node<T> current = getByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value,int index) {
        return setByIndex(value,index).value;
    }

    @Override
    public T remove(int index) {
        return removeByIndex(index).value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = tail;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.value, object)) {
                removeByIndex(i);
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
        return size == 0;
    }

    private void insert(T value,int index) {
        Node<T> nodeForeAdd;
        Node<T> current = tail;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        nodeForeAdd = new Node<>(current, value, current.next);
        current.next = nodeForeAdd;
        current.next.prev = nodeForeAdd;
    }

    private Node<T> setByIndex(T value,int index) {
        Objects.checkIndex(index,size);
        Node<T> current = getByIndex(index);
        Node<T> oldNode = new Node<>(current.prev, current.value, current.next);
        current.value = value;
        return oldNode;
    }

    private Node<T> getByIndex(int index) {
        Objects.checkIndex(index,size);
        Node<T> current = tail;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> removeByIndex(int index) {
        Objects.checkIndex(index,size);
        Node<T> nodeForRemove;
        if (index == 0) {
            nodeForRemove = tail;
            tail = tail.next;
            if (tail == null) {
                head = null;
            }
        } else {
            nodeForRemove = getByIndex(index);
            if (index == size - 1) {
                head = nodeForRemove.prev;
                size--;
                return nodeForRemove;
            }
            nodeForRemove.next.prev = nodeForRemove.prev;
            nodeForRemove.prev.next = nodeForRemove.next;
        }
        size--;
        return nodeForRemove;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(Node<T> prev,T value) {
            this.value = value;
            this.prev = prev;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
