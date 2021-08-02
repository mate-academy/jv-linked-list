package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int listSize = 0;
    private Node<T> first;
    private Node<T> last;

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

    public void checkIndex(int index) {
        if (index < 0 || index > listSize - 1) {
            throw new IndexOutOfBoundsException("No such index in the list");
        }
    }

    public Node<T> nodeByIndex(int index) {
        if (index < (listSize >> 1)) {
            Node<T> newNode = first;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        } else {
            Node<T> newNode = last;
            for (int i = listSize - 1; i > index; i--) {
                newNode = newNode.prev;
            }
            return newNode;
        }
    }

    void addBefore(T value, Node<T> nodeAfter) {
        Node<T> nodeBefore = nodeAfter.prev;
        Node<T> addedNode = new Node<>(nodeBefore, value, nodeAfter);
        nodeAfter.prev = addedNode;
        nodeBefore.next = addedNode;
        listSize++;
    }

    void unlink(Node<T> value) {
        Node<T> next = value.next;
        Node<T> prev = value.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            value.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            value.next = null;
        }
        listSize--;
    }

    @Override
    public void add(T value) {
        if ((value == null && listSize == 0) || (value != null && listSize == 0)) {
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
            first.next = last;
            last.prev = first;
        } else {
            Node<T> lastCopy = last;
            Node<T> newLast = new Node<>(lastCopy, value, null);
            last = newLast;
            lastCopy.next = newLast;
            first.prev = null;
        }
        listSize++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > listSize) {
            throw new IndexOutOfBoundsException("No such index in the list");
        }

        if (listSize == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
            first.next = last;
            last.prev = first;
            listSize++;
        } else if (index == 0) {
            Node<T> firstCopy = first;
            Node<T> newNode = new Node<>(null, value, first);
            firstCopy.prev = newNode;
            first = newNode;
            listSize++;
        } else if (index == listSize) {
            Node<T> lastCopy = last;
            Node<T> newNode = new Node<>(last, value, null);
            lastCopy.next = newNode;
            last = newNode;
            listSize++;
        } else {
            addBefore(value, nodeByIndex(index));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addAll(List<T> list) {
        Object[] inputList = list.toArray();
        int inputLength = inputList.length;

        if (inputLength > 0) {
            for (Object o : inputList) {
                T value = (T) o;
                add(value);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = nodeByIndex(index);
        T oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue = nodeByIndex(index).value;
        unlink(nodeByIndex(index));

        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            int index = 0;
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.value == null) {
                    unlink(nodeByIndex(index));
                    return true;
                }
                index++;
            }
        } else {
            int index = 0;
            for (Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.value)) {
                    unlink(nodeByIndex(index));
                    return true;
                }
                index++;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        if (listSize == 0) {
            return true;
        } else {
            return false;
        }
    }
}
