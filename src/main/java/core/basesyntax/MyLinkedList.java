package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        addToLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addToLast(value);
        } else {
            addBefore(value, getCurrentNode(index));
        }
    }

    private void addToLast(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void addBefore(T value, Node<T> currentNode) {
        Node<T> beforeNode = currentNode.prev;
        Node<T> newNode = new Node<>(beforeNode, value, currentNode);
        currentNode.prev = newNode;
        if (beforeNode == null) {
            head = newNode;
        } else {
            beforeNode.next = newNode;
        }
        size++;
    }

    public Node<T> getCurrentNode(int index) {
        rangeCheck(index);

        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void rangeCheck(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index more than size or less than 0");
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            T value = list.get(i);
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        if (index == size) {
            throw new IndexOutOfBoundsException("Index equal size");
        }
        return getCurrentNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("Index equal size");
        }
        rangeCheck(index);

        Node<T> nodeForReplace = getCurrentNode(index);
        T oldValue = nodeForReplace.value;
        nodeForReplace.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        return unlink(getCurrentNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            Node<T> removeObject = head;
            while (removeObject != null) {
                if (removeObject.value == null) {
                    unlink(removeObject);
                    return true;
                }
                removeObject = removeObject.next;
            }
        } else {
            Node<T> removeObject = head;
            while (removeObject != null) {
                if (object.equals(removeObject.value)) {
                    unlink(removeObject);
                    return true;
                }
                removeObject = removeObject.next;
            }
        }
        return false;
    }

    private T unlink(Node<T> removeNode) {
        if (removeNode == null) {
            throw new IndexOutOfBoundsException("Node doesn't exist");
        }
        Node<T> removeNext = removeNode.next;
        Node<T> removePrev = removeNode.prev;

        if (removePrev == null) {
            head = removeNext;
        } else {
            removePrev.next = removeNext;
            removeNode.prev = null;
        }
        if (removeNext == null) {
            tail = removePrev;
        } else {
            removeNext.prev = removePrev;
            removeNode.next = null;
        }
        T removeValue = removeNode.value;
        removeNode.value = null;
        size--;
        return removeValue;
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
