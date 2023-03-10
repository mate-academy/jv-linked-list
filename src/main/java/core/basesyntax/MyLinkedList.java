package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        this.size = 0;
    }

    @Override
    public void add(T value) {
        addTail(value);
    }

    @Override
    public void add(T value, int index) {
        int linkedListSize = this.size;
        checkOnIndexOutOfBoundsException(index, false);

        if (linkedListSize == 0) {
            addHead(value);
        } else if (linkedListSize == index) {
            addTail(value);
        } else {
            Node<T> findedNode = findNode(index);
            addBetween(value, findedNode);
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
        checkOnIndexOutOfBoundsException(index, true);
        Node<T> needNode = findNode(index);
        return needNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkOnIndexOutOfBoundsException(index, true);
        Node<T> needNode = findNode(index);
        T oldValue = needNode.value;
        needNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkOnIndexOutOfBoundsException(index, true);
        Node<T> node = findNode(index);
        T removeValue = node.value;
        removeNode(node);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        boolean isNode = false;
        Node<T> node = this.head;
        if (this.size != 0) {
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
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private void addHead(T value) {
        Node<T> temp = this.head;
        Node<T> node = new Node<>(null, value, temp);
        this.head = node;
        if (temp == null) {
            this.tail = node;
        } else {
            temp.prev = node;
        }
        this.size++;
    }

    private void addTail(T value) {
        Node<T> temp = this.tail;
        Node<T> node = new Node<>(temp, value, null);
        this.tail = node;
        if (temp == null) {
            this.head = node;
        } else {
            temp.next = node;
        }
        this.size++;
    }

    private void addBetween(T value, Node<T> findNode) {
        Node<T> prev = findNode.prev;
        Node<T> newNode = new Node<>(prev, value, findNode);
        findNode.prev = newNode;
        if (prev == null) {
            this.head = newNode;
        } else {
            prev.next = newNode;
        }
        this.size++;
    }

    private Node<T> findNode(int index) {
        int sizeLinkedList = this.size;
        if (sizeLinkedList == 0) {
            return this.head;
        }
        if (index == sizeLinkedList) {
            return this.tail;
        }

        Node<T> needNode;
        int middle = sizeLinkedList / 2;
        if (index > middle) {
            needNode = this.tail;
            while (sizeLinkedList - 1 != index) {
                needNode = needNode.prev;
                sizeLinkedList--;
            }
        } else {
            int checkIndex = 0;
            needNode = this.head;
            while (checkIndex != index) {
                needNode = needNode.next;
                checkIndex++;
            }
        }
        return needNode;
    }

    private void removeNode(Node<T> nodeForRemove) {
        Node<T> prev = nodeForRemove.prev;
        Node<T> next = nodeForRemove.next;
        if (prev == null && next == null) {
            this.head = null;
            this.tail = null;
            this.size = 0;
        } else {
            if (prev == null) {
                this.head = next;
                next.prev = null;
            } else if (next == null) {
                this.tail = prev;
                prev.next = null;
            } else {
                prev.next = next;
                next.prev = prev;
            }
            this.size--;
        }
    }

    private void checkOnIndexOutOfBoundsException(int index, boolean isGet) {
        int newIndexForGet = index;
        if (isGet) {
            newIndexForGet++;
        }

        if (newIndexForGet > this.size || index < 0) {
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
