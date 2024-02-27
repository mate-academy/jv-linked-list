package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node head;
    private Node tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node(null, value, null);
            tail = head;
        } else if (size > 0) {
            Node lastNode = tail;
            tail = new Node(lastNode, value, null);
            lastNode.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size >= 0 && index == size) {
            add(value);
        } else {
            addByIndex(value, index);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        isIndexExist(index);
        Node node = findNodeByIndex(index);
        return (T) node.value;
    }

    @Override
    public T set(T value, int index) {
        isIndexExist(index);
        Node node = findNodeByIndex(index);
        T oldValue = (T) node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexExist(index);
        T value = (T) findNodeByIndex(index).value;
        unlink(findNodeByIndex(index));
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node node = head;
        for (int i = 0; i < size; i++) {
            if (needToRemove(object, node)) {
                unlink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private boolean needToRemove(T object, Node node) {
        return (object == null && node.value == null)
                || (node.value != null && node.value.equals(object));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void isIndexExist(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void addByIndex(T value, int index) {
        isIndexExist(index);
        Node node = findNodeByIndex(index);
        if (index == 0) {
            head = new Node(node.prev, value, node);
            return;
        }
        Node previousNod = node.prev;
        node.prev = new Node(node.prev, value, node);
        previousNod.next = node.prev;

    }

    private Node findNodeByIndex(int index) {
        if (index == 0) {
            return head;
        }
        if (index == size) {
            return tail;
        }
        if (size / 2 > index) {
            return findFromHead(index);
        } else {
            return findFromTail(index);
        }
    }

    private Node findFromHead(int index) {
        Node fromNode = head;
        for (int i = 0; i != index; i++) {
            fromNode = fromNode.next;
        }
        return fromNode;
    }

    private Node findFromTail(int index) {
        Node fromNode = tail;
        for (int i = size - 1; i != index; i--) {
            fromNode = fromNode.prev;
        }
        return fromNode;
    }

    private void unlink(Node node) {
        if (head == tail) {
            size--;
            head = null;
            tail = null;
            return;
        } else if (node == head) {
            Node nextNode = node.next;
            nextNode.prev = null;
            node.next = null;
            head = nextNode;
            size--;
            return;
        } else if (node == tail) {
            Node previousNode = node.prev;
            previousNode.next = null;
            node.prev = null;
            tail = previousNode;
            size--;
            return;
        } else {
            Node preciousNode = node.prev;
            Node nextNode = node.next;
            preciousNode.next = nextNode;
            nextNode.prev = preciousNode;
            node.next = null;
            node.prev = null;
            size--;
        }
    }
}

