package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(value, null, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(value, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0 && head == null) {
            add(value);
        } else if (index == 0 && head != null) {
            Node<T> newNode = new Node<>(value, null, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else {
            Node<T> previousNode = iteratorByIndex(index - 1);

            if (previousNode.next == null) {
                Node<T> newNode = new Node<>(value, previousNode, null);
                previousNode.next = newNode;
                previousNode = newNode;
                size++;
            } else {
                Node<T> newNode = new Node<>(value, previousNode, previousNode.next);
                previousNode.next = newNode;
                newNode.next.prev = newNode;
                size++;
            }
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
        return iteratorByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = iteratorByIndex(index).value;
        iteratorByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = iteratorByIndex(index);
        removeNode(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = iteratorByValue(object);
        if (removedNode == null) {
            return false;
        }
        removeNode(removedNode);
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

    private Node<T> iteratorByIndex(int index) {
        if (isEmpty() || index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index [" + index + "] out of bounds!");
        }
        Node<T> tmp = head;
        for (int indexCount = 0; indexCount < size; indexCount++) {

            if (indexCount == index) {
                return tmp;
            }
            tmp = tmp.next;
        }
        return null;
    }

    private Node<T> iteratorByValue(T value) {
        if (isEmpty()) {
            throw new NullPointerException("LinkedList is empty");
        }
        Node<T> tmp = head;
        for (int indexCount = 0; indexCount < size; indexCount++) {
            if (value == null) {
                if (value == tmp.value) {
                    return tmp;
                } else {
                    tmp = tmp.next;
                }
            } else {
                if (value.equals(tmp.value)) {
                    return tmp;
                } else {
                    tmp = tmp.next;
                }
            }
        }
        return null;
    }

    private void removeNode(Node<T> removedNode) {
        if (removedNode == head && removedNode == tail) {
            removedNode = null;
            head = null;
            tail = null;
            size--;
        } else if (removedNode == head) {
            removedNode.next.prev = null;
            head = removedNode.next;
            size--;
        } else if (removedNode == tail) {
            removedNode.prev.next = null;
            tail = removedNode.prev;
            size--;
        } else {
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
            size--;
        }
    }

    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
