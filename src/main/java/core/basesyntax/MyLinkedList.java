package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev,T data,Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null,value,null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node newNode = new Node(null, value, null);
        if (index == size) {
            add(value);
            size--;
        }
        if (index == 0) {
            getNodeByIndex(index).prev = newNode;
            newNode.next = getNodeByIndex(index);
            head = newNode;
        }
        if (index > 0 && index < size) {
            newNode.prev = getNodeByIndex(index - 1);
            newNode.next = getNodeByIndex(index);
            getNodeByIndex(index).prev = newNode;
            getNodeByIndex(index - 1).next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        indexValidation(index);
        return (T) getNodeByIndex(index).data;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        T oldValue = getNodeByIndex(index).data;
        getNodeByIndex(index).data = (T) value;
        return (T) oldValue;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        T oldValue = getNodeByIndex(index).data;
        unlinkNode(getNodeByIndex(index));
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.data == object
                    || currentNode.data != null
                    && currentNode.data.equals(object)) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
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

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index >= size / 2) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void unlinkNode(Node node) {
        if (node == null) {
            return;
        }
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
        size--;
    }
}
