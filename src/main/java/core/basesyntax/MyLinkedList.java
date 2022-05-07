package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        indexCheckForAdding(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> node = node(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            if (node.prev == null) {
                head = newNode;
            } else {
                node.prev.next = newNode;
            }
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> copyNode = node(index);
        T copyValue = copyNode.element;
        copyNode.element = value;
        return copyValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        final Node<T> nodeCopy = node(index);
        unlink(node(index));
        size--;
        return nodeCopy.element;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = head; i != null; i = i.next) {
                if (i.element == null) {
                    unlink(i);
                    size--;
                    return true;
                }
            }
        } else {
            for (Node<T> i = head; i != null; i = i.next) {
                if (object.equals(i.element)) {
                    unlink(i);
                    size--;
                    return true;
                }
            }
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

    private void indexCheck(int index) {
        if (index >= 0 && index < size) {
            return;
        }
        throw new IndexOutOfBoundsException("Wrong index. Index should be: 0 <= index <= "
                + (size - 1));
    }

    private void indexCheckForAdding(int index) {
        if (index >= 0 && index <= size) {
            return;
        }
        throw new IndexOutOfBoundsException("Wrong index. Index should be: 0 <= index <= "
                + size);
    }

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private void unlink(Node<T> nodeCopy) {
        if (nodeCopy.prev == null && nodeCopy.next == null) {
            head = null;
            tail = null;
        } else if (nodeCopy.prev == null) {
            head = nodeCopy.next;
            nodeCopy.next.prev = null;
        } else if (nodeCopy.next == null) {
            tail = nodeCopy.prev;
            nodeCopy.prev.next = null;
        } else {
            nodeCopy.prev.next = nodeCopy.next;
            nodeCopy.next.prev = nodeCopy.prev;
        }
    }

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

}
