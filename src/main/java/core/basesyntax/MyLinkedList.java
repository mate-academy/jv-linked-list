package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    @Override
    public void add(T value) {
        addTail(value);
    }

    @Override
    public void add(T value, int index) {
        checkOnIndexOutOfBoundsException(index, false);

        if (size == 0) {
            addHead(value);
        } else if (size == index) {
            addTail(value);
        } else {
            Node<T> findedNode = findNode(index);
            addBefore(value, findedNode);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addTail(value);
        }
    }

    @Override
    public T get(int index) {
        return findNodeAndCheckIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNodeAndCheckIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = findNodeAndCheckIndex(index);
        T removeValue = node.value;
        removeNode(node);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        boolean isNode = false;
        Node<T> node = head;
        if (size != 0) {
            do {
                T value = node.value;
                if ((value == null && object == null)
                        || (value != null && node.value.equals(object))) {
                    removeNode(node);
                    isNode = true;
                    break;
                } else {
                    node = node.next;
                }
            } while (node.next != null);
        }
        return isNode;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addHead(T value) {
        Node<T> temp = head;
        Node<T> node = new Node<>(null, value, temp);
        head = node;
        if (temp == null) {
            tail = node;
        } else {
            temp.prev = node;
        }
        size++;
    }

    private void addTail(T value) {
        Node<T> temp = tail;
        Node<T> node = new Node<>(temp, value, null);
        tail = node;
        checkOnNull(temp, node);
        size++;
    }

    private void addBefore(T value, Node<T> findNode) {
        Node<T> prev = findNode.prev;
        Node<T> newNode = new Node<>(prev, value, findNode);
        findNode.prev = newNode;
        checkOnNull(prev, newNode);
        size++;
    }

    private Node<T> findNodeAndCheckIndex(int index) {
        checkOnIndexOutOfBoundsException(index, true);
        return findNode(index);
    }

    private void checkOnNull(Node<T> checkNode, Node<T> useNode) {
        if (checkNode == null) {
            head = useNode;
        } else {
            checkNode.next = useNode;
        }
    }

    private Node<T> findNode(int index) {
        int counter = size;
        if (counter == 0) {
            return head;
        }
        if (index == counter) {
            return tail;
        }

        Node<T> needNode;
        int middle = counter / 2;
        if (index > middle) {
            needNode = tail;
            while (counter - 1 != index) {
                needNode = needNode.prev;
                counter--;
            }
        } else {
            counter = 0;
            needNode = head;
            while (counter != index) {
                needNode = needNode.next;
                counter++;
            }
        }
        return needNode;
    }

    private void removeNode(Node<T> nodeForRemove) {
        Node<T> prev = nodeForRemove.prev;
        Node<T> next = nodeForRemove.next;
        if (prev == null && next == null) {
            head = null;
            tail = null;
            size = 0;
        } else {
            if (prev == null) {
                head = next;
            } else if (next == null) {
                tail = prev;
            } else {
                prev.next = next;
                next.prev = prev;
            }
            size--;
        }
    }

    private void checkOnIndexOutOfBoundsException(int index, boolean isGetIndex) {
        int newIndexForGet = index;
        if (isGetIndex) {
            newIndexForGet++;
        }

        if (newIndexForGet > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
