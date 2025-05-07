package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private ListNode head;
    private ListNode tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        ListNode newNode = new ListNode<>(value);
        newNode.setPrev(tail);
        if (size == 0) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        ListNode newNode = new ListNode<>(value);
        if (index == 0 && size > 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        if (size == index) {
            add(value);
            return;
        }
        ListNode temp = findElementByIndex(index);
        newNode.next = temp;
        newNode.prev = temp.prev;
        temp.prev = newNode;
        newNode.prev.next = newNode;
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
        ListNode temp = findElementByIndex(index);
        return (T) temp.value;
    }

    @Override
    public T set(T value, int index) {
        ListNode temp = findElementByIndex(index);
        T oldValue = (T) temp.value;
        temp.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        ListNode temp = findElementByIndex(index);
        unlink(temp);
        return (T) temp.value;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        boolean found = false;
        ListNode temp = head;
        if (temp.value != null && temp.value.equals(object)
                || temp.value == object) {
            unlink(head);
            found = true;
            return found;
        }
        while (temp.next != null) {
            if (temp.value != null && temp.value.equals(object)
                    || temp.value == object) {
                unlink(temp);
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
        return head == null;
    }

    private ListNode findElementByIndex(int index) {
        if (index < 0 || index > size - 1 || isEmpty()) {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
        if (index > size / 2) {
            ListNode temp = tail;
            index = size - index - 1;
            for (int i = index; i != 0; i--) {
                temp = temp.prev;
            }
            return temp;
        }
        ListNode temp = head;
        for (int i = index; i != 0; i--) {
            temp = temp.next;
        }
        return temp;
    }

    private void unlink(ListNode<T> unlinkElement) {
        if (unlinkElement == head && size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (unlinkElement == tail) {
            tail.prev.next = null;
            tail = tail.prev;
            size--;
            return;
        }
        if (unlinkElement == head) {
            head = unlinkElement.next;
            size--;
            return;
        }
        unlinkElement.next.prev = unlinkElement.prev;
        unlinkElement.prev.next = unlinkElement.next;
        size--;
    }

    private static class ListNode<T> {
        private T value;
        private ListNode prev;
        private ListNode next;

        ListNode(T value) {
            this.value = value;
        }

        public ListNode getNext() {
            return next;
        }

        public ListNode getPrev() {
            return prev;
        }

        public T getValue() {
            return value;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public void setPrev(ListNode prev) {
            this.prev = prev;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
