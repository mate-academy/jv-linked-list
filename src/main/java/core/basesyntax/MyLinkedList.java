package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS = "Index is out of bounds of list";
    private ListNode<T> head;
    private ListNode<T> tail;
    private int size;

    private class ListNode<T> {
        private T value;
        private ListNode prev;
        private ListNode next;

        ListNode(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new ListNode<>(value);
            tail = head;
            size = 1;
        } else {
            tail.next = new ListNode<>(value);
            tail.next.prev = tail;
            tail = tail.next;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        ListNode<T> node = getListNode(index);
        ListNode<T> newNode = new ListNode<>(value);
        boolean isAfterTailInsert = false;
        if (checkIndexOutOfBorder(index)) {
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
                size++;
                return;
            }
            node = tail;
            isAfterTailInsert = true;
        }
        if (isAfterTailInsert) {
            newNode.prev = node;
            node.next = newNode;
            tail = newNode;
        } else {
            newNode.next = node;
            newNode.prev = node.prev;
            if (newNode.prev != null) {
                newNode.prev.next = newNode;
            }
            node.prev = newNode;
            if (index == 0) {
                head = newNode;
            }
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
        checkIndexOutOfBorderThrowException(index);
        ListNode<T> node = getListNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOutOfBorderThrowException(index);
        ListNode<T> node = getListNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfBorderThrowException(index);
        ListNode<T> node = getListNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        ListNode<T> nextNode = head;

        if (nextNode == null) {
            return false;
        }

        while (!(nextNode.value == object
                || nextNode.value != null && nextNode.value.equals(object))) {
            nextNode = nextNode.next;
            if (nextNode == null) {
                break;
            }
        }
        if (nextNode == null) {
            return false;
        }
        unlink(nextNode);
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

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
    }

    private boolean checkIndexOutOfBorder(int index) {
        return size == 0 || index == size;
    }

    private void checkIndexOutOfBorderThrowException(int index) {
        if (size == 0 || index == size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
    }

    private void unlink(ListNode<T> node) {
        ListNode<T> nextNode = node.next;
        ListNode<T> prevNode = node.prev;

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = size == 1 ? head : prevNode;
        }
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }
        size--;
        if (isEmpty()) {
            head = null;
            tail = null;
        }
    }

    private ListNode<T> getListNode(int index) {
        checkIndex(index);

        if (index == 0) {
            return head;
        }

        if (index == size - 1) {
            return tail;
        }
        int middleOfList = size / 2;
        ListNode<T> nextNode = head;
        if (index < middleOfList) {
            for (int i = 1; i <= index; i++) {
                nextNode = nextNode.next;
            }
        } else {
            nextNode = tail;
            for (int i = size - 1; i > index; i--) {
                nextNode = nextNode.prev;
            }
        }
        return nextNode;
    }
}
