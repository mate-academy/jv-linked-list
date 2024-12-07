package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private T data;
        private Node left;
        private Node right;
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private void checkExceptionIndex(int index) {
        if(index < 0 || index > size - 1 || (this.isEmpty() && index > 0)) {
            throw new IndexOutOfBoundsException("Element not found in the list");
        }
    }

    @Override
    public void add(T value) {
        Node new_node = new Node();
        new_node.data = value;
        if (this.isEmpty()) {
            new_node.left = null;
            new_node.right = null;
            head = new_node;
            tail = new_node;
            size++;
            return;
        }
        tail.right = new_node;
        new_node.left = tail;
        tail = new_node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if(size != index) {
            checkExceptionIndex(index);
        }
        Node new_node = new Node();
        new_node.data = value;
        size++;
        if(this.isEmpty() && index == 0) {
            new_node.left = null;
            new_node.right = null;
            head = tail = new_node;
            return;
        }
        if(index == 0) {
            new_node.right = head;
            new_node.left = null;
            if(head != null) {
                head.left = new_node;
            }
            head = new_node;
            return;
        }
        if(index == size - 1) {
            tail.right = new_node;
            new_node.left = tail;
            tail = new_node;
            return;
        }
        Node temp_node = head;
        for (int i = 0; i < index; i++) {
            temp_node = temp_node.right;
        }
        temp_node.left.right = new_node;
        new_node.right = temp_node;
        new_node.left = temp_node.left;
        temp_node.left = new_node;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkExceptionIndex(index);
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.right;
        }
        return temp.data;
    }

    @Override
    public T set(T value, int index) {
        checkExceptionIndex(index);
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.right;
        }
        T old = temp.data;
        temp.data = value;
        return old;
    }

    @Override
    public T remove(int index) {
        checkExceptionIndex(index);
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.right;
        }
        if (temp == head) {
            if(this.size > 1) {
                head = temp.right;
                temp.right = null;
                head.left = null;
            } else {
                head = null;
                tail = null;
            }
        } else if (temp == tail) {
            temp.left.right = null;
        } else {
            temp.left.right = temp.right;
            temp.right.left = temp.left;
        }
        size--;
        return temp.data;
    }

    @Override
    public boolean remove(T object) {
        Node temp = head;
        for (int i = 0; i < size; i++) {
            if ((temp.data != null && temp.data.equals(object))
            || (temp.data == null && null == object)) {
                this.remove(i);
                return true;
            }
            temp = temp.right;
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
}
