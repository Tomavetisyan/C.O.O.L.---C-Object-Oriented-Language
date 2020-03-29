package parser;

import java.util.List;
import parser.statements.Statement;
import parser.statements.BlockStmt;

public class Constructor{

    final public List<Statement> parameters;
    final public BlockStmt body;

    public Constructor(final List<Statement> parameters, final BlockStmt body){
        this.parameters = parameters;
        this.body = body;
    }

    public String toString(){
        return "Constructor(" + parametersString() + ", " + body + ")";
    }

    private String parametersString(){
        String ret = "";
        if(parameters.size() >= 1){
            ret += parameters.get(0).toString();
        }
        for(int i = 1; i < parameters.size(); i++){
            ret += ", " + parameters.get(i).toString();
        }
        return ret;
    }

    public boolean equals(Constructor constructor) {
        return (this.toString()).equals(constructor.toString());
    }
}