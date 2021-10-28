package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;
    private int size;

    private static class Node<E> {
        private E item;
        private MyLinkedList.Node<E> next;
        private MyLinkedList.Node<E> prev;

        Node(MyLinkedList.Node<E> prev, E element, MyLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        MyLinkedList.Node<T> lastNode = last;
        MyLinkedList.Node<T> newNode =
                new MyLinkedList.Node<T>(last, value, (MyLinkedList.Node<T>) null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            size++;
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node<T>(null, value, first);
            first = newNode;
        } else {
            size++;
            MyLinkedList.Node<T> prevNodeByIndex = node(index - 1);
            MyLinkedList.Node<T> nextNode = prevNodeByIndex.next;
            MyLinkedList.Node<T> newNode =
                    new MyLinkedList.Node<T>(prevNodeByIndex, value, nextNode);
            nextNode.prev = newNode;
            prevNodeByIndex.next = newNode;
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
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        MyLinkedList.Node<T> currentNode = node(index);
        T oldVal = currentNode.item;
        currentNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        MyLinkedList.Node<T> currentNode;
        if (object == null) {
            for (currentNode = first; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.item == null) {
                    unlink(currentNode);
                    return true;
                }
            }
        } else {
            for (currentNode = first; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.item)) {
                    unlink(currentNode);
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private MyLinkedList.Node<T> node(int index) {
        MyLinkedList.Node<T> x;
        int i;
        if (index < size / 2) {
            x = first;
            for (i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            x = last;
            for (i = size - 1; i > index; --i) {
                x = x.prev;
            }
            return x;
        }
    }

    private T unlink(MyLinkedList.Node<T> currentNode) {
        MyLinkedList.Node<T> nextNode = currentNode.next;
        MyLinkedList.Node<T> prevNode = currentNode.prev;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            currentNode.prev = null;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            currentNode.next = null;
        }
        T element = currentNode.item;
        currentNode.item = null;
        size--;
        return element;
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(5);
        list.add(Integer.valueOf(15), 0);
        System.out.println(list.get(0));
    }
}
