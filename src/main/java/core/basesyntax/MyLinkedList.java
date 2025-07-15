package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {

        Node<T> node;

        if (last == null) {
            node = new Node<>(null,value,null);
            first = node;
        } else {
            Node<T> l = last;
            node = new Node<>(l,value,null);
            l.next = node;
        }

        last = node;
        size++;

    }

    @Override
    public void add(T value, int index) {

        if ((size > 0 && index > size) || index < 0) {
            throw new IndexOutOfBoundsException("Index is out by size of List!");
        }

        Node<T> currentNode;
        Node<T> prev;
        Node<T> node;

        if (size == 0) {
            node = new Node<>(null,value,null);
            first = node;
            last = node;

            size++;

        } else {

            currentNode = toGetElementByIndex(index);
            if (currentNode != null) {
                prev = (currentNode.prev != null) ? currentNode.prev : null;
                node = new Node<>(prev,value,currentNode);

                currentNode.prev = node;
                if (prev != null) {
                    prev.next = node;
                }

                if (index == 0) {
                    first = node;
                }

                size++;

            } else if (index == size) {
                node = new Node<>(last,value,null);

                prev = last;
                prev.next = node;

                if (index == size) {
                    last = node;
                }

                size++;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {

        if (list != null) {
            int listSize = list.size();
            for (int i = 0;i < listSize;i++) {
                add(list.get(i));
            }
        }

    }

    @Override
    public T get(int index) {

        if ((size > 0 && index > size - 1) || index < 0) {
            throw new IndexOutOfBoundsException("Index is out by size of List!");
        }

        Node<T> currentNode = toGetElementByIndex(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {

        if ((size > 0 && index > size - 1) || index < 0) {
            throw new IndexOutOfBoundsException("Index is out by size of List!");
        }

        Node<T> currentNode = toGetElementByIndex(index);

        T oldValue = currentNode.item;
        currentNode.item = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {

        if ((size > 0 && index > size - 1) || index < 0) {
            throw new IndexOutOfBoundsException("Index is out by size of List!");
        }

        Node<T> currentNode = toGetElementByIndex(index);
        changeNodes(currentNode);

        size--;

        return (T) currentNode.item;
    }

    @Override
    public boolean remove(T object) {

        Node<T> currentNode = toGetElementByValue(object);
        if (currentNode != null) {
            changeNodes(currentNode);
            size--;
            return true;
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

    private static class Node<T> {

        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }

    private Node<T> toGetElementByValue(T element) {

        Node<T> nextNode = first;

        try {
            do {
                if (nextNode.item != null && element != null && nextNode.item.equals(element)) {
                    return nextNode;
                } else if (nextNode.item == null && element == null) {
                    return nextNode;
                }
                if (nextNode.next != null) {
                    nextNode = nextNode.next;
                } else {
                    nextNode = null;
                }

            } while (nextNode != null);

        } catch (Exception e) {
            throw new NullPointerException("Item node`s is null");
        }

        return null;

    }

    public Node<T> toGetElementByIndex(int index) {

        Node<T> currentNode = null;
        
        if (index == 0) {
            currentNode = first;
        //} else if (index == size) {
        //    currentNode = last;
        } else {
            try {
                if (index <= (size >> 1)) {
                    for (int i = 0; i <= index; i++) {
                        if (i == 0) {
                            currentNode = first;
                        } else {
                            currentNode = currentNode.next;
                        }
                    }
                } else {
                    for (int i = (size - 1); i >= index; i--) {
                        if (i == (size - 1)) {
                            currentNode = last;
                        } else {
                            currentNode = currentNode.prev;
                        }
                    }
                }
            } catch (Exception e) {
                throw new NullPointerException();
            }
        }

        return currentNode;
    }

    public void changeNodes(Node<T> currentNode) {

        Node<T> prevNode;
        Node<T> nextNode;

        if (currentNode == first || currentNode == last) {
            if (currentNode == first) {

                if (currentNode.next != null) {
                    nextNode = currentNode.next;
                    nextNode.prev = null;
                    first = nextNode;
                } else if (size == 1) {
                    first = null;
                }

                currentNode.next = null;
            }

            if (currentNode == last) {

                if (currentNode.prev != null) {
                    prevNode = currentNode.prev;
                    prevNode.next = null;
                    last = prevNode;
                } else if (size == 1) {
                    last = null;
                }

                currentNode.prev = null;
            }

        } else {

            prevNode = currentNode.prev;
            nextNode = currentNode.next;

            prevNode.next = nextNode;
            nextNode.prev = prevNode;

            currentNode.prev = null;
            currentNode.next = null;

        }
    }

}
