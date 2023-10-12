## Common mistakes (jv-linked-list)

#### Don't begin class or method implementation with an empty line. 
Remove all redundant empty lines, be careful :)
#### Don't use class Objects.
#### Make `Node` an inner class.
The purpose of inner classes is to group classes that belong together, which makes your code more readable and maintainable.
#### When creating a new `Node`, pass all the fields to the `Node` constructor.
#### Don't use getters and setters in class Node
We have access to private fields of the inner class, so using getters or setters is redundant.
#### Private methods and classes should be after public ones in your class.
#### Create informative variable and method names.
Writing proper variable names can be a highly valuable investment in the quality of your code. 
Not only you and your teammates understand your code better, but it can also improve code sustainability in the future. 
When you go back to the same code base and re-read it over again, you should understand what is going on.
Do not use abstract words like `string` or `array` as variable names. Do not use one-letter names. The name of the method should make it clear what it does.

- Bad example:
    ```java
    String[] arr = new String[]{"Alex", "Bob", "Alice"};
    for (String s : arr) {
        System.out.println(s);
    }
    ```
- Refactored code:
    ```java
    String[] usernames = new String[]{"Alex", "Bob", "Alice"};
    for (String username : usernames) {
        System.out.println(username);
    }
    ```
#### Don't complicate if-else construction. [Detailed explanation.](https://mate-academy.github.io/jv-program-common-mistakes/java-core/complicated-if-else.html)
#### Don't create repeating code.
If the logic of your code repeats - move it to a separate private method. 
Remember about [DRY and KISS](https://dzone.com/articles/software-design-principles-dry-and-kiss) principles.
#### Don't create redundant variables.
Redundant variables are confusing and make your code less clean and much more difficult to read. Not to mention they occupy stack memory.
#### Don't forget about access modifiers.
You should never want to expose the object fields directly. They should be accessed through special methods (getters and setters).
#### If you tested in `main()`, don't forget to remove it as well as all commented code.
#### Don't forget that you can have `null` as a method parameter in the `remove(T object) method`.
#### Don't iterate your nodes from the head to the tail or vice versa.
Pay attention to the index you receive as an input, you can iterate only the first or second half of the list, 
depending on the index value. That will boost your lists' performance.
#### Create a separate method `unlink(Node node)`.
This logic will be used in 2 methods: when we remove from the list by index and by value. So let's create a method that will take a Node 
that needs to be removed and unlink it. As a result, we will call it from both methods when Node is found.
#### Be careful with linking Nodes.
Make sure that you add mutual links from both sides when you insert a new Node, and that you remove all the links when you remove a Node.
