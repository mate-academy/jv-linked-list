package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        checkSizeLessZero(size);
        if (size == 0) {
            head = newNode;
        } else if (size > 0) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(tail, value, null);
        checkSizeLessZero(size);
        checkIndex(size, index);
        addSizeIfIndexInTheMidle(index, newNode);
        if (index == 0) {
            newNode.next = head;
            newNode.prev = null;
            head = newNode;
            if (size == 0) {
                tail = newNode;
            } else {
                head.prev = newNode;
            }
            size++;
        }
        if (index == size) {
            addByIndexToTheTail(newNode);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = head;
        return getNodeByIndex(index);
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = (Node<T>) getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = (Node<T>) getNodeByIndex(index);
        if (node != null) {
            unlink(node);
            return true;
        }
        return false;
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = (Node<T>) getNodeByValue(object);
        if (node != null) {
            unlink(node);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private T getNodeByIndex(int index) {
        Node<T> current = head;
        if (index >= size || (index < 0)) {
            throw new IndexOutOfBoundsException("Index wrong");
        } else {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current.value;
    }

    private T getNodeByValue(T object) {
        Node<T> node = (Node<T>) getNodeByValue(object);
        if (node != null) {
            unlink(node);
            return true;
        }
        return false;
        return node.value;
            }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return node.value;
    }

    private void addByIndexToTheAtMiddle(Node<T> newNode, int index, int size) {
        Node<T> temp = null;
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            temp = current.prev;
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev = newNode;
            temp.next = newNode;
        } else if (index >= size / 2) {
            Node<T> current = tail;
            for (int i = size; i > index; i--) {
                current = current.prev;
                temp = current.next;
            }
            newNode.prev = current;
            newNode.next = temp;
            current.next = newNode;
            temp.prev = newNode;
        }
    }


    private void addByIndexToTheTail(Node<T> newNode) {
        newNode.next = null;
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    private void checkIndex(int size, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index wrong");
        }
    }

    private void checkSizeLessZero(int size) {
        if (size < 0) {
            throw new IndexOutOfBoundsException("Size wrong");
        }
    }

    private int addSizeIfIndexInTheMidle(int index, Node<T> newNode) {
        if (index > 0 && index < size) {
            addByIndexToTheAtMiddle(newNode, index, size);
            size++;
        }
        return size;
    }
}

