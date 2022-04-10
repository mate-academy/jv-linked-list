package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int listCount;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(value);
        if (first == null) {
            newNode.next = null;
            newNode.prev = null;
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        listCount++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > listCount) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        Node<T> newNode = new Node(value);
        if (index == 0 && listCount == 0) {
            add(value);
        } else if (index == 0 && listCount > 0) {
            newNode.next = first;
            newNode.prev = null;
            first.prev = newNode;
            first = newNode;
            listCount++;
        } else if (index == listCount) {
            add(value);
        } else {
            Node<T> oldNode = first;
            for (int i = 0; i < index; i++) {
                oldNode = oldNode.next;
            }
            addItem(oldNode, newNode);
            listCount++;

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
        if (index < 0 || index >= listCount) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        Node<T> node = first;
        if (index == 0) {
            return (T) first.value;
        }
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return (T) node.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= listCount) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        Node<T> oldNode = first;
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = first.next;
            newNode.prev = first.prev;
            first.next.prev = newNode;
            first = newNode;
        } else if (index == listCount - 1) {
            oldNode = last;
            newNode.next = null;
            newNode.prev = last.prev;
            last.prev.next = newNode;
            newNode = last;
        } else {
            for (int i = 0; i < index; i++) {
                oldNode = oldNode.next;
            }
            addItem(oldNode, newNode);
        }
        return (T) oldNode.value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= listCount) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        Node<T> oldNode = first;
        if (index == 0 && size() == 1) {
            first = null;
            last = null;
        } else if (index == 0) {
            first.next.prev = null;
            first = first.next;
        } else if (index == listCount - 1) {
            oldNode = last;
            last.prev.next = null;
            last.prev = last;
            last.prev = null;
        } else {
            for (int i = 0; i < index; i++) {
                oldNode = oldNode.next;
            }
            oldNode.prev.next = oldNode.next;
            oldNode.next.prev = oldNode.prev;
        }
        listCount--;
        return oldNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            throw new IllegalArgumentException("list is empty");
        }
        Node<T> oldNode = first;
        Node<T> lastNode = last;
        if (object == null) {
            for (int i = 0; i < listCount; i++) {
                if (oldNode.value == null) {
                    Node<T> oldNodePrevios = oldNode.prev;
                    oldNodePrevios.next = oldNode.next;
                    oldNode.next.prev = oldNodePrevios;
                    listCount--;
                    return true;
                }
                oldNode = oldNode.next;
            }
        } else if (object.equals(oldNode.value) && size() == 1) {
            first = null;
            last = null;
            listCount--;
            return true;
        } else if (object.equals(oldNode.value) && size() > 1) {
            first.next.prev = null;
            first = first.next;
            listCount--;
            return true;
        } else {
            for (int i = 0; i < listCount; i++) {
                if (object.equals(oldNode.value) && oldNode == last) {
                    last.prev.next = null;
                    last.prev = last;
                    listCount--;
                    return true;
                } else if (object.equals(oldNode.value)) {
                    Node<T> oldNodePrevios = oldNode.prev;
                    oldNodePrevios.next = oldNode.next;
                    oldNode.next.prev = oldNodePrevios;
                    listCount--;
                    return true;
                }
                oldNode = oldNode.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return listCount;
    }

    @Override
    public boolean isEmpty() {
        return listCount == 0;
    }

    private void addItem(Node<T> oldNode, Node<T> newNode) {
        Node<T> oldNodePrevios = oldNode.prev;
        newNode.next = oldNode;
        newNode.prev = oldNodePrevios;
        oldNodePrevios.next = newNode;
        oldNode.prev = newNode;
    }
}
