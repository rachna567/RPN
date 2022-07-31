# Task : Create a RPN Calculator

# class : Main.java
# method rpnCalc | main | testOperand

* In this class, a main() method reads every time a command line input and create Scanner class object to read the nextLine then
calls the rpnCalc(). rpnCalc() splits the string from whitespace and retrieve the every token of the String.

Algorithm:  
1: When user input a String, every token of the string is retrieved and converted into a BigDecimal value
2: If the token is a digit then the precision is set to 15 and push into the stack
3: Using reflection api the multiply, addition, subtract and divide function of Operation class is invoked.
4. If the operation is undo or clear then directly undo and clear methods are called
5: Final result of the stack is displayed.


# class : Operation.java
# method template | addition | subtract | multiply | divide | sqrt | undo | toPlainBigDecimal
* In this class, a common template method is created for the binary operation which has an execute method. This method is called
for addition, subtraction, multiplication and division. Similarly for the unary operation such as sqrt an another template method
is created which accepts unary action. Template method also stores the last Stack State values and last token. 

Algorithm: 
1: When user input request for Addition 
2: template method of Operation class calls
3: Store the lastStackState values and last token
4. Call stack.pop() to retrieve the first operand and second operand
5: Call the execute() of functional interface BinaryAction which calls the add method on first and second operand
6: Set the scale of result by calling toPlainBigDecimal method
