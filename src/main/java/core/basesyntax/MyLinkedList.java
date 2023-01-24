package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node tail;
    private Node head;
    private int size;

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    private class Node<T> {
        private Node left;
        private Node right;
        private T value;

        public Node(Node left, Node right, T value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public T getValue() {
            return value;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private void unlink(Node node) {
        // 1 - node - 3
        if (node.left == null) {
            head = node.right;
        } else {
            node.left.right = node.right;
        }
        if (node.right == null) {
            tail = node.left;
        } else {
            node.right.left = node.left;
        }
    }

    private Node getNode(int index) {
        int count = 0;
        Node pointer = head;
        while (pointer != null) {
            if (count == index) {
                return pointer;
            }
            pointer = pointer.right;
            count++;
        }
        return null;
    }

    private void testIndex(int index) {
        if (index >= size() || index < 0) {
            //  if(index >= size || index < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(T value) {
        Node newNoge = new Node(tail, null, value);
        if (head == null) {
            head = newNoge;
        }
        if (tail != null) {
            tail.right = newNoge;
        }
        tail = newNoge;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size()) {
            add(value);
            return;
        }
        testIndex(index);
        // int count = 0;
        Node pointer = getNode(index); // 1 - newNode - pointer
        Node leftNode = null;
        if (pointer != null) {
            leftNode = pointer.left;
        }
        Node newNode = new Node(leftNode, pointer, value);
        if (leftNode != null) {
            leftNode.right = newNode;
        }
        if (pointer != null) {
            pointer.left = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        testIndex(index);
        Node pointer = getNode(index);
        if (pointer == null) {
            return null;
        } else {
            return (T) pointer.getValue();
        }
    }

    @Override
    public T set(T value, int index) {
        testIndex(index);
        int count = 0;
        Node<T> pointer = head;
        T oldValue = null;
        while (pointer != null) {
            if (index == count) {
                oldValue = pointer.getValue();
                pointer.setValue(value);
                break;
            } else {
                pointer = pointer.right;
                count++;
            }
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        testIndex(index);
        T toReturn = null;
        Node<T> pointer = getNode(index);
        if (pointer != null) {
            T result = pointer.getValue();
            unlink(pointer);
            size--;
            return result;
        }
        return toReturn;
    }

    @Override
    public boolean remove(T object) {
        Node<T> pointer = head;
        while (pointer != null) {
            if (pointer.getValue() == object) {
                unlink(pointer);
                size--;
                return true;
            }
            pointer = pointer.right;
        }
        return false;
    }

    @Override
    public int size() {
    /*    int count = 0;
        Node pointer = head;
        while (pointer != null) {
            pointer = pointer.right;
            count++;
        }
      */
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
