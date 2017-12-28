# Groove Language Grammar

## The grammar uses the following BNF-style conventions:

- S* denotes zero or more occurrences of S
- S? denotes zero or one occurrence of S
- S+ denotes one or more occurrences of S
- X | Y means one of either X or Y

## Grammar:

##### program
>: [declarations](#declarations)

##### declarations
>: [package_declaration](#package_declaration)  ([import_file](#import_file) | [function_declaration](#function_declaration) | [declaration_list](#declaration_list))*

##### package_declaration
>: PACKAGE ID (DOT ID)*

##### import_file
>: IMPORT ID (DOT (ID | MUL_OP))*

##### function_declaration
>: LET FUNCTION ID L_PAREN [parameters](#parameters) R_PAREN BE [type_spec](#type_spec) [compound_statement](#compound_statement)


##### parameters
>: [type_spec](#type_spec) [variable](#variable) (COMMA [type_spec](#type_spec) [variable](#variable))*

##### declaration_list
>: [declaration](#declaration)+

##### declaration
>: LET [init_declarator_list](#init_declarator_list) BE [type_spec](#type_spec) EOL+

##### init_declarator_list
>: [init_declarator](#init_declarator) (COMMA [init_declarator](#init_declarator))*

##### init_declarator
>: [variable](#variable) (ASSIGN [assignment_expression](#assignment_expression))?

##### statement
>: [iteration_statement](#iteration_statement) <br>
>| [selection_statement](#selection_statement) <br>
>| [jump_statement](#jump_statement) <br>
>| [compound_statement](#compound_statement) <br>
>| [expression_statement](#expression_statement) <br>


##### compound_statement
>: COLON EOL+ ([declaration_list](#declaration_list) | [statement](#statment))* END

##### jump_statement
>: RETURN [expression](#expression)? EOL+<br>
>| BREAK EOL+<br>
>| CONTINUE EOL+

##### selection_statement
>: IF L_PAREN [expression](#expression) R_PAREN [statement](#statement) (ELSE [statement](#statement))?

##### iteration_statement
>: WHILE L_PAREN [expression](#expression) R_PAREN [statement](#statement)<br>
>| DO [statement](#statement) WHILE L_PAREN [expression](#expression) R_PAREN EOL+<br>
>|FOR L_PAREN [expression_statement](#expression_statement)(NO_EOL) COLON [expression_statement](#expression_statement)(NO_EOL) COLON ([expression](#expression))? R_PAREN [statement](#statement)

##### expression_statement
>: [expression](#expression)* EOL+

##### expression
>: [assignment_expression](#assignment_expression) (COMMA [assignment_expression](#assignment_expression))*

##### assignment_expression
>: [assignment_expression](#assignment_expression) (COMMA [assignment_expression](#assignment_expression))* <br>
>| [conditional_expression](#conditional_expression)

##### conditional_expression
>: [logical_and_expression](#logical_and_expression) (QUESTION_MARK [expression](#expression) COLON [conditional_expression](#conditional_expression))?

##### logical_and_expression
>: [logical_or_expression](#logical_or_expression) (LOG_AND_OP [logical_or_expression](#logical_or_expression))*

##### logical_or_expression
>: [inclusive_or_expression](#inclusive_or_expression) (LOG_OR_OP [inclusive_or_expression](#inclusive_or_expression))*


##### inclusive_or_expression
>: [exclusive_or_expression](#exclusive_or_expression) (OR_OP [exclusive_or_expression](#exclusive_or_expression))*

##### exclusive_or_expression
>: [and_expression](#and_expression) (XOR_OP [and_expression](#and_expression))*

##### and_expression
>: [equality_expression](#equality_expression) (AND_OP [equality_expression](#equality_expression))*

##### equality_expression
>: [relational_expression](#relational_expression) ((EQ_OP | NE_OP) [relational_expression](#relational_expression))*

##### relational_expression
>: [shift_expression](#shift_expression) ((LE_OP | LT_OP | GE_OP | GT_OP)[shift_expression](#shift_expression))*

##### shift_expression
>: [additive_expression](#additive_expression) ((LEFT_OP | RIGHT_OP) [additive_expression](#additive_expression))*

##### additive_expression
>: [multiplicative_expression](#multiplicative_expression) ((ADD_OP | SUB_OP) [multiplicative_expression](#multiplicative_expression))*

##### multiplicative_expression
>: [power_expression](#power_expression)   ((MUL_OP | DIV_OP | MOD_OP) [power_expression](#power_expression))*

##### power_expression
>: [cast_expression](#cast_expression) (POW_OP [cast_expression](#cast_expression))*

##### cast_expression
>: L_PAREN [type_spec](#type_spec) R_PAREN [cast_expression](#cast_expression)<br>
>| [unary_expression](#unary_expression)

##### unary_expression
>: INC_OP [unary_expression](#unary_expression)<br>
>| DEC__OP [unary_expression](#unary_expression)<br>
>| AND_OP [cast_expression](#cast_expression)<br>
>| ADD_OP [cast_expression](#cast_expression)<br>
>| SUB_OP [cast_expression](#cast_expression)<br>
>| LOG_NEG [cast_expression](#cast_expression)<br>
>| [postfix_expression](#postfix_expression)

##### postfix_expression
>: [primary_expression](#primary_expression) INC_OP <br>
>| [primary_expression](#primary_expression) DEC_OP <br>
>| [primary_expression](#primary_expression) L_PAREN [argument_expression_list](#argument_expression_list)? R_PAREN

##### argument_expression_list
>: [assignment_expression](#assignment_expression) (COMMA [assignment_expression](#assignment_expression))*

##### primary_expression
>: L_PAREN [expression](#expression) R_PAREN<br>
>| [constant](#constant)<br>
>| [string](#string)<br>
>| [variable](#variable)

##### constant
>: INTEGER_CONSTANT<br>
>| REAL_CONSTANT<br>
>| CHAR_CONSTANT<br>
>| BOOLEAN_CONSTANT

##### type_spec
>:  VOID<br>    |
>   BOOLEAN<br> |
>   INT<br>     |
>   LONG<br>    |
>   FLOAT<br>   |
>   DOUBLE<br>  |
>   CHAR<br>    |
>   STRING

##### variable
>: ID

##### string
>: STRING_CONSTANT