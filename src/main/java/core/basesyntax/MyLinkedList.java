package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private ListNode head;
    private ListNode tail;
    private int size;

    public MyLinkedList() {
    }

    public MyLinkedList(T value) {
        head = new ListNode<>(null, value, null);
        tail = head;
        size++;
    }

    @Override
    public void add(T value) {
        if (head == null) {
            addFirst(value);
        } else {
            tail.next = new ListNode<>(tail, value, null);
            tail = tail.next;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Bad index: " + index + " for size: " + size);
        }
        if (size == 0) {
            addFirst(value);
            return;
        } else if (index == 0) {
            head = new ListNode<>(null, value, head);
            head.next.prev = head;
            size++;
            return;
        } else if (index == size) {
            addLast(value);
            return;
        } else {
            ListNode<T> current = findByIndex(index);
            ListNode<T> newNode = new ListNode<>(current.prev, value, current);
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkBound(index);
        ListNode<T> current = findByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkBound(index);
        ListNode<T> current = findByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkBound(index);
        ListNode<T> current = findByIndex(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        ListNode current = head;
        while (current != null) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
                break;
            }
            current = current.next;
        }
        if (current == null) {
            return false;
        }
        unlink(current);
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private boolean checkBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Bad index: " + index + " for size: " + size);
        }
        return true;
    }

    private T unlink(ListNode<T> node) {
        ListNode<T> next = node.next;
        ListNode<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return node.value;
    }

    private void addFirst(T value) {
        head = tail = new ListNode<>(null, value, null);
        size++;
    }

    private void addLast(T value) {
        tail = new ListNode<>(tail, value, null);
        tail.prev.next = tail;
        size++;
    }

    private ListNode<T> findByIndex(int index) {
        ListNode current = head;
        int i = 0;
        while (i != index) {
            current = current.next;
            i++;
        }
        return current;
    }

    private class ListNode<T> {
        private T value;
        private ListNode next;
        private ListNode prev;

        public ListNode(ListNode<T> prev, T element, ListNode<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
