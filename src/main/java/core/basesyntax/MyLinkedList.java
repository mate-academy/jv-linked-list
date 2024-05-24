package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int NUMBER_ONE = 1;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        addToLastPosition(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0) {
            addToFirstPosition(value);
        } else if (index == size) {
            addToLastPosition(value);
        } else {
            addInsideList(index, value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            addToLastPosition(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGet(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        removeNode(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNodeByValue(object);
        return removeNode(currentNode);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addToFirstPosition(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        if (head != null) {
            head.prev = newNode;
        } else {
            tail = newNode;
        }
        head = newNode;
        size++;
    }

    private void addToLastPosition(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    private void addInsideList(int index, T value) {
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        addNodeInsideList(newNode, currentNode);
    }

    private void addNodeInsideList(Node<T> newNode, Node<T> oldNode) {
        if (oldNode == head) {
            addToFirstPosition(newNode.value);
        } else {
            newNode.prev = oldNode.prev;
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
            newNode.next = oldNode;
        }
        size++;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || (size == 0 ? index > 0 : index > size)) {
            throw new IndexOutOfBoundsException("incorrect index");
        }
    }

    private void checkIndexForGet(int index) {
        if (index < 0 || (size == 0 ? index > 0 : index >= size)) {
            throw new IndexOutOfBoundsException("incorrect index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndexForGet(index);
        Node<T> currentNode = null;
        if (index <= size / 2) {
            int count = 0;
            currentNode = head;
            while (count != index) {
                if (index == count++) {
                    break;
                }
                currentNode = currentNode.next;
            }
        } else {
            int count = size - NUMBER_ONE;
            currentNode = tail;
            while (count != index) {
                if (index == count--) {
                    break;
                }
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private boolean removeNode(Node<T> currentNode) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode == head && currentNode == tail) {
            head = null;
            tail = null;
        } else if (currentNode == head) {
            if (currentNode.next != null) {
                currentNode.next.prev = null;
            }
            head = currentNode.next;
        } else if (currentNode == tail) {
            currentNode.prev.next = null;
            tail = currentNode.prev;
        } else {
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
        }
        size--;
        return true;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (value != null
                    && value.equals(currentNode.value) || value == currentNode.value) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
