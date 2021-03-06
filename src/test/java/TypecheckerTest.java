import java.util.*;

import parser.*;
import parser.statements.*;
import parser.expressions.*;
import tokenizer.*;
import tokenizer.tokens.*;
import tokenizer.tokens.keywords.*;
import tokenizer.tokens.operatortokens.*;

import typechecker.*;
import typechecker.types.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TypecheckerTest {

        @Test
        public void testInherit() {
                Type mytype = new IntType();
                assertTrue(mytype instanceof IntType);
        }

        @Test
        public void testSimplestProgram() throws IllTypedException {
                // Note: Don't need an assert if the test is expected to pass

                // "int main(){return 0;}"
                List<Statement> mainStatements = new ArrayList<Statement>();
                // mainStatements.add(new ReturnStmt(new IntegerExp(0)));
                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);
        }

        @Test
        public void testValidPrimitiveDeclaration() throws IllTypedException {
                /*
                 * int main(){ int a; int b = 1; return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("int", "a"));
                mainStatements.add(new VariableDeclarationStmt("int", "b", new IntegerExp(1)));

                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);

                /*
                 * int main(){ String a; String b = "text"; return 0;}
                 */
                mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("String", "a"));
                mainStatements.add(new VariableDeclarationStmt("String", "b", new StringExp("text")));

                myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                myTypechecker = new Typechecker(myProgram);

                /*
                 * int main(){ Bool a; Bool b = 1 == 1; return 0;}
                 */
                mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("Bool", "a"));
                mainStatements.add(new VariableDeclarationStmt("Bool", "b",
                                new EqualEqualExp(new IntegerExp(1), new IntegerExp(1))));

                myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                myTypechecker = new Typechecker(myProgram);
        }

        @Test
        public void testInvalidPrimitiveDeclaration() throws IllTypedException {
                /*
                 * int main(){ int a = "text"; return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("int", "a", new StringExp("text")));

                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));

                try {
                        Typechecker myTypechecker = new Typechecker(myProgram);
                        fail("IllTypedException should have been thrown");
                } catch (IllTypedException e) {
                }

                /*
                 * int main(){ String a = 1; return 0;}
                 */
                mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("String", "a", new IntegerExp(1)));

                myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                try {
                        Typechecker myTypechecker = new Typechecker(myProgram);
                        fail("IllTypedException should have been thrown");
                } catch (IllTypedException e) {
                }
        }

        @Test
        public void testValidPrimitiveAssignment() throws IllTypedException {
                /*
                 * int main(){ int a; a = 1; return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("int", "a"));
                mainStatements.add(new VariableAssignmentStmt("a", new IntegerExp(1)));

                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);

                /*
                 * int main(){ String a; a = "text"; return 0;}
                 */
                mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("String", "a"));
                mainStatements.add(new VariableAssignmentStmt("a", new StringExp("text")));

                myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                myTypechecker = new Typechecker(myProgram);

                /*
                 * int main(){ Bool a; a = 1 == 1; return 0;}
                 */
                mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("Bool", "a"));
                mainStatements.add(new VariableAssignmentStmt("a",
                                new EqualEqualExp(new IntegerExp(1), new IntegerExp(1))));

                myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                myTypechecker = new Typechecker(myProgram);
        }

        @Test
        public void testInvalidPrimitiveAssignment() throws IllTypedException {
                /*
                 * int main(){ int a; a = "text"; return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("int", "a"));
                mainStatements.add(new VariableAssignmentStmt("a", new StringExp("text")));

                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                try {
                        Typechecker myTypechecker = new Typechecker(myProgram);
                        fail("IllTypedException should have been thrown");
                } catch (IllTypedException e) {
                }

                /*
                 * int main(){ String a; a = 1; return 0;}
                 */
                mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("String", "a"));
                mainStatements.add(new VariableAssignmentStmt("a", new IntegerExp(1)));

                myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                try {
                        Typechecker myTypechecker = new Typechecker(myProgram);
                        fail("IllTypedException should have been thrown");
                } catch (IllTypedException e) {
                }
        }

        @Test
        public void testPrintStatements() throws IllTypedException {
                /*
                 * int main(){ print(1); print("text"); println(1); println("text"); return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new PrintStmt(new IntegerExp(1)));
                mainStatements.add(new PrintStmt(new StringExp("text")));
                mainStatements.add(new PrintlnStmt(new IntegerExp(1)));
                mainStatements.add(new PrintlnStmt(new StringExp("text")));

                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));

                Typechecker myTypechecker = new Typechecker(myProgram);
        }

        @Test
        public void testWhileLoops() throws IllTypedException {
                /*
                 * int main(){ while(1 == 1){} return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new WhileStmt(new EqualEqualExp(new IntegerExp(1), new IntegerExp(1)),
                                new BlockStmt(new ArrayList<Statement>())));

                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);
        }

        @Test
        public void testForLoops() throws IllTypedException {
                /*
                 * int main(){ for(int i = 0; i < 1; i = i + 1){} return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new ForStmt(new VariableDeclarationStmt("int", "i", new IntegerExp(0)),
                                new LessThanExp(new VariableExp("i"), new IntegerExp(1)),
                                new VariableAssignmentStmt("i", new PlusExp(new VariableExp("i"), new IntegerExp(1))),
                                new BlockStmt(new ArrayList<Statement>())));

                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);
        }

        @Test
        public void testIfElse() throws IllTypedException {
                /*
                 * int main(){ if(1 == 1){} return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new IfStmt(new EqualEqualExp(new IntegerExp(1), new IntegerExp(1)),
                                new BlockStmt(new ArrayList<Statement>())));
                Program myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);
                /*
                 * int main(){ if(1 == 1){} else {} return 0;}
                 */
                mainStatements = new ArrayList<Statement>();
                mainStatements.add(new IfElseStmt(new EqualEqualExp(new IntegerExp(1), new IntegerExp(1)),
                                new BlockStmt(new ArrayList<Statement>()), new BlockStmt(new ArrayList<Statement>())));
                myProgram = new Program(new ArrayList<ClassDef>(),
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                myTypechecker = new Typechecker(myProgram);
        }

        @Test
        public void testValidSubtypingAssignment() throws IllTypedException {
                /*
                 * int main(){ ParentClass a; a = new ChildClass(); return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("ParentClass", "a"));
                mainStatements.add(new VariableAssignmentStmt("a", new NewExp("ChildClass", new ArrayList<Expression>())));

                ArrayList<VariableDeclarationStmt> fields = new ArrayList<VariableDeclarationStmt>();
                Constructor constructor = new Constructor(fields, new BlockStmt(new ArrayList<Statement>()));
                ArrayList<MethodDef> methods = new ArrayList<MethodDef>();
                ArrayList<ClassDef> classdefs = new ArrayList<ClassDef>();
                
                ClassDef parent = new ClassDef("ParentClass", "", fields, constructor, methods);
                ClassDef child = new ClassDef("ChildClass", "ParentClass", fields, constructor, methods);
                classdefs.add(parent);
                classdefs.add(child);

                Program myProgram = new Program(classdefs,
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);
        }

        @Test(expected = IllTypedException.class)
        public void testInvalidSubtypingAssignment() throws IllTypedException {
                /*
                 * int main(){ ParentClass a; a = new ChildClass(); return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("ParentClass", "a"));
                mainStatements.add(new VariableAssignmentStmt("a", new NewExp("ChildClass", new ArrayList<Expression>())));

                ArrayList<VariableDeclarationStmt> fields = new ArrayList<VariableDeclarationStmt>();
                Constructor constructor = new Constructor(fields, new BlockStmt(new ArrayList<Statement>()));
                ArrayList<MethodDef> methods = new ArrayList<MethodDef>();
                ArrayList<ClassDef> classdefs = new ArrayList<ClassDef>();
                
                ClassDef parent = new ClassDef("ParentClass", "", fields, constructor, methods);
                ClassDef child = new ClassDef("ChildClass", "", fields, constructor, methods);
                classdefs.add(parent);
                classdefs.add(child);

                Program myProgram = new Program(classdefs,
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);
        }

        @Test
        public void testValidPolymorphismMethodCall() throws IllTypedException {
                /*
                 * int main(){ ParentClass a; a = new ChildClass(); return 0;}
                 */
                List<Statement> mainStatements = new ArrayList<Statement>();
                mainStatements.add(new VariableDeclarationStmt("ChildClass", "a", new NewExp("ChildClass", new ArrayList<Expression>())));
                mainStatements.add(new VariableDeclarationStmt("int", "x", new MethodCallExp("a", "parentMethod", new ArrayList<Expression>())));

                ArrayList<VariableDeclarationStmt> fields = new ArrayList<VariableDeclarationStmt>();
                Constructor constructor = new Constructor(fields, new BlockStmt(new ArrayList<Statement>()));
                ArrayList<MethodDef> methods = new ArrayList<MethodDef>();
                ArrayList<ClassDef> classdefs = new ArrayList<ClassDef>();
                ClassDef child = new ClassDef("ChildClass", "ParentClass", fields, constructor, new ArrayList<MethodDef>());
                MethodDef parentMethod = new MethodDef("int", "parentMethod", new ArrayList<VariableDeclarationStmt>(), new BlockStmt(new ArrayList<Statement>()), new IntegerExp(0));
                methods.add(parentMethod);
                ClassDef parent = new ClassDef("ParentClass", "", fields, constructor, methods);

                classdefs.add(parent);
                classdefs.add(child);

                Program myProgram = new Program(classdefs,
                                new MethodDef("int", "main", new ArrayList<VariableDeclarationStmt>(),
                                                new BlockStmt(mainStatements), new IntegerExp(0)));
                Typechecker myTypechecker = new Typechecker(myProgram);
        }
}