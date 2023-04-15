package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS = "Index is out of bounds of list";
    private ListNode<T> head;
    private ListNode<T> tail;
    private int size = 0;

    public MyLinkedList() {

    }

    public MyLinkedList(T value) {
        head = new ListNode<>(value);
        tail = head;
        size = 1;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new ListNode<>(value);
            tail = head;
            size = 1;
        } else {
            tail.setNext(new ListNode<>(value));
            tail.getNext().setPrev(tail);
            tail = tail.getNext();
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        ListNode<T> node = getListNode(index);
        ListNode<T> newNode = new ListNode<>(value);
        boolean isAfterTailInsert = false;
        if (node == null) {
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
            newNode.setPrev(node);
            node.setNext(newNode);
            tail = newNode;
        } else {
            newNode.setNext(node);
            newNode.setPrev(node.getPrev());
            if (newNode.getPrev() != null) {
                newNode.getPrev().setNext(newNode);
            }
            node.setPrev(newNode);
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

    private ListNode<T> getListNode(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }

        if (size == 0 || index == size) {
            return null;
        }

        if (index == 0) {
            return head;
        }

        if (index == size - 1) {
            return tail;
        }

        ListNode<T> nextNode = head;
        for (int i = 1; i <= index; i++) {
            nextNode = nextNode.getNext();
        }
        return nextNode;
    }

    @Override
    public T get(int index) {
        ListNode<T> node = getListNode(index);
        if (node == null) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
        return node.getValue();
    }

    @Override
    public T set(T value, int index) {
        ListNode<T> node = getListNode(index);
        if (node == null) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    private void removeNode(ListNode<T> node) {
        ListNode<T> nextNode = node.getNext();
        ListNode<T> prevNode = node.getPrev();

        if (nextNode != null) {
            nextNode.setPrev(prevNode);
        } else {
            tail = size == 1 ? head : prevNode;
        }
        if (prevNode != null) {
            prevNode.setNext(nextNode);
        } else {
            head = nextNode;
        }
        size--;
        if (isEmpty()) {
            head = null;
            tail = null;
        }
    }

    @Override
    public T remove(int index) {
        ListNode<T> node = getListNode(index);
        if (node == null) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
        removeNode(node);
        return node.getValue();
    }

    @Override
    public boolean remove(T object) {
        ListNode<T> nextNode = head;

        if (nextNode == null) {
            return false;
        }

        while (!(nextNode.getValue() == object
                || nextNode.getValue() != null && nextNode.getValue().equals(object))) {
            nextNode = nextNode.getNext();
            if (nextNode == null) {
                break;
            }
        }
        if (nextNode == null) {
            return false;
        }
        removeNode(nextNode);
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
}
