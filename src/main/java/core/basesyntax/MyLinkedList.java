package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node head;
    private Node tail;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            tail = new Node(value);
            head = tail;
            wire(head, tail);
        } else {
            Node tmp = tail;
            tail = new Node(value);
            wire(tmp, tail);
        }
        size += 1;

        return true;
    }

    @Override
    public void add(T value,int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        Node target = getNodeByIndex(index);
        Node newNode = null;

        if (index == 0) {
            head = new Node(value);
            if (target == null) {
                add(value);
                return;
            }

            wire(head, target);
        } else if (index == size) {
            add(value);
            return;
        } else {
            newNode = new Node(value);
            wire(target.prev, newNode);
            wire(newNode, target);
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value,int index) {
        validateIndex(index);
        Node target = getNodeByIndex(index);
        T result = target.value;
        target.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node target = getNodeByIndex(index);
        T result = target.value;
        if (size == 1) {
            tail = null;
            head = null;
        } else if (size == 2) {
            if (target.value.equals(head.value)) {
                head = tail;
            } else {
                tail = head;
            }
            wire(head, tail);
        } else if (index == 0) {
            head = target.next;
            head.prev = head;
        } else if (index == size - 1) {
            tail = target.prev;
            tail.next = null;
        } else {
            wire(target.prev, target.next);
        }
        size--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        Node target = getNodeByValue(t);
        if (target == null) {
            return false;
        }

        if (size == 1) {
            tail = null;
            head = null;
        } else if (size == 2) {
            if (target.value == head.value || target.value.equals(head.value)) {
                head = tail;
            } else {
                tail = head;
            }
            wire(head, tail);
            release(target);
        } else if (head.value == t || head.value.equals(t)) {
            head = target.next;
            head.prev = head;
            release(target);
        } else {
            if (target != tail) {
                wire(target.prev, target.next);
            } else if (tail.value == t || tail.value.equals(t)) {
                tail = target.prev;
                tail.next = null;
            }
            release(target);
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
        return (size == 0);
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    private void release(Node node) {
        node.prev = null;
        node.next = null;
    }

    private void wire(Node node1, Node node2) {
        node1.next = node2;
        node2.prev = node1;
    }

    private Node getNodeByIndex(int index) {
        Node result = null;

        if (index < size / 2) {
            result = head;
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    return result;
                }
                result = result.next;
            }
        } else {
            result = tail;
            for (int i = size - 1; i >= index; i--) {
                if (i == index) {
                    return result;
                }
                result = result.prev;
            }
        }
        return result;
    }

    private Node getNodeByValue(T value) {
        Node result = head;

        if (result == null) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (result.value == value
                    || result.value != null && result.value.equals(value)) {
                return result;
            }
            result = result.next;
            if (result == null) {
                return null;
            }
        }
        return result;
    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        Node(T value) {
            this.value = value;
        }
    }
}


