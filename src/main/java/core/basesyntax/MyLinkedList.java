package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        validatePositionIndex(index);
        if (index == size) {
            addLast(value);
        } else {
            addBefore(value, getNode(index));
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
        validateIndex(index);
        Node<T> serchNode = getNode(index);
        return serchNode.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> updatedNode = getNode(index);
        T oldValue = updatedNode.value;
        updatedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> deletedNode = getNode(index);
        unlink(index, deletedNode);
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(removeNode.value, object)) {
                unlink(i, removeNode);
                return true;
            }
            removeNode = removeNode.next;
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

    private void addLast(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void validatePositionIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsMessage(index));
        }
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(getIndexOutOfBoundsMessage(index));
        }
    }

    private String getIndexOutOfBoundsMessage(int index) {
        return "Index: " + index + " out of range";
    }

    private void addBefore(T value, Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        node.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> serchNode;
        if (index < size / 2) {
            serchNode = head;
            for (int i = 0; i < index; i++) {
                serchNode = serchNode.next;
            }
        } else {
            serchNode = tail;
            for (int i = size - 1; i > index; i--) {
                serchNode = serchNode.prev;
            }
        }
        return serchNode;
    }

    private void unlink(int index, Node<T> unlinkNode) {
        if (index == 0) {
            unlinkFirst(unlinkNode);
        } else if (index == size - 1) {
            unlinkLast(unlinkNode);
        } else {
            unlinkNode.prev.next = unlinkNode.next;
            unlinkNode.next.prev = unlinkNode.prev;
        }
        size--;
    }

    private void unlinkFirst(Node<T> unlinkNode) {
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            unlinkNode.next.prev = null;
            head = unlinkNode.next;
        }
    }

    private void unlinkLast(Node<T> unlinkNode) {
        tail = unlinkNode.prev;
        unlinkNode.prev.next = null;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
