package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE = "Index does not exist";
    private ListNode<T> head;
    private ListNode<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        ListNode<T> newNode = new ListNode<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head = new ListNode<T>(null, value, head);
            size++;
            return;
        }
        checkIndex(index);
        ListNode<T> nextNode = getNodeByIndex(index);
        ListNode<T> previousNode = nextNode.prev;
        ListNode<T> newNode = new ListNode(previousNode, value, nextNode);
        previousNode.next = newNode;
        nextNode.prev = newNode;
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
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        ListNode<T> newNode = getNodeByIndex(index);
        T oldVal = newNode.value;
        newNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        ListNode<T> removedNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, removedNode.value)) {
                unlink(removedNode);
                return true;
            }
            removedNode = removedNode.next;
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

    private T unlink(ListNode<T> nodeByIndex) {
        ListNode<T> nextNode = nodeByIndex.next;
        ListNode<T> prevNode = nodeByIndex.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            nodeByIndex.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            nodeByIndex.next = null;
        }
        T node = nodeByIndex.value;
        nodeByIndex.value = null;
        size--;
        return node;
    }

    ListNode<T> getNodeByIndex(int index) {
        ListNode<T> node = null;
        if (index <= size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
    }

    private class ListNode<T> {
        private T value;
        private ListNode<T> prev;
        private ListNode<T> next;

        public ListNode(ListNode<T> prev, T value, ListNode<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public void setPrev(ListNode<T> prev) {
            this.prev = prev;
        }

        public void setNext(ListNode<T> next) {
            this.next = next;
        }
    }
}
