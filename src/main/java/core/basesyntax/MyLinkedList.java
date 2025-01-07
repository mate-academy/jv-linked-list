package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = new Node<T>(null, null);
        tail = head;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            tail.setItem(value);
            size++;
            return;
        }
        Node<T> newNode = new Node<>(null, tail);
        tail.next = newNode;
        tail = newNode;
        newNode.setItem(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> target = getNadeByIndex(index);

        if (this.size == 0) {
            Node<T> addedNode = new Node<>(null, null);
            head = addedNode;
            tail = addedNode;
            addedNode.setItem(value);
            this.size++;
            return;
        } else if (index == 0) {
            Node<T> addedNode = new Node<>(head, null);
            head.prev = addedNode;
            head = addedNode;
            addedNode.setItem(value);
            this.size++;
            return;
        } else if (index == size) {
            Node<T> addedNode = new Node<>(null, tail);
            tail.next = addedNode;
            tail = addedNode;
            addedNode.setItem(value);
            this.size++;
            return;
        } else {
            Node<T> addedNode = new Node<>(target, target.prev);
            target.prev.next = addedNode;
            target.prev = addedNode;
            addedNode.setItem(value);
            this.size++;
            return;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        Node<T> target = getNadeByIndex(index);
        if (target == null) {
            throw new IndexOutOfBoundsException("Can't get object");
        }
        return target.getItem();
    }

    @Override
    public T set(T value, int index) {
        Node<T> target = getNadeByIndex(index);
        if (target == null) {
            throw new IndexOutOfBoundsException("Can't set object");
        }
        T oldItem = target.getItem();
        target.setItem(value);
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> target = getNadeByIndex(index);
        if (size == 1) {
            Node<T> addedNode = new Node<>(null, null);
            head = addedNode;
            tail = addedNode;
            this.size--;
            return target.getItem();
        }

        if (target == tail) {
            tail = tail.prev;
            tail.next = null;
            size--;
            return target.getItem();
        }

        if (target == head) {
            head = head.next;
            head.prev = null;
            size--;
            return target.getItem();
        }
        if (target == null) {
            throw new IndexOutOfBoundsException("Can't remove object");
        }

        target.next.prev = target.prev;
        target.prev.next = target.next;
        size--;
        return target.getItem();
    }

    @Override
    public boolean remove(T object) {
        Node<T> target = getNodeByValue(object);
        if (target == null) {
            return false;
        }

        if (size == 1) {
            head = null;
            tail = null;
            head = new Node<T>(null, null);
            tail = head;
        } else if (target == head) {
            head = head.next;
            head.prev = null;
        } else if (target == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            target.prev.next = target.next;
            target.next.prev = target.prev;
        }
        size--;
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

    private Node<T> getNadeByIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't set element");
        }

        Node<T> target = head;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> target = head;
        while (target != null) {
            if (value == null ? target.getItem() == null : value.equals(target.getItem())) {
                return target;
            }
            target = target.next;
        }
        return null; // Повертаємо null, якщо елемент не знайдено
    }

    static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> next, Node<T> prev) {
            this.next = next;
            this.prev = prev;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }
    }
}
