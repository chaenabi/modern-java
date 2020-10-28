/*
package com.company.javac;

import com.sun.source.tree.*;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.swing.text.Utilities;
import javax.tools.Diagnostic;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegExpRule implements Rule {
    @Override
    public void analyseFile(AnalysisContext ctx, CompilationUnitTree file) {
        //throw new UnsupportedOperationException("Not supported yet."); // To change

        new TreePathScanner<Void, Void>() {
            @Override
            public Void visitVariable(VariableTree node, Void unused) {
                Element varEl = ctx.getTrees().getElement(getCurrentPath());
                if (Utilities.findAttachedAnnotation(ctx, varEl, RegExp.class.getName()) != null) {
                    if(node.getInitializer() != null && node.getInitializer().getKind() == Tree.Kind.STRING_LITERAL) {
                        String constant = getConstant(ctx, new TreePath(getCurrentPath(), node.getInitializer()));

                        if(constant != null) {
                            try {
                                Pattern.compile(constant);
                            } catch (PatternSyntaxException ex) {
                                ctx.getTrees().printMessage(Diagnostic.Kind.WARNING,
                                        ex.getDescription(),
                                        node.getInitializer(),
                                        file);
                            }
                        }
                    }
                }
                return super.visitVariable(node, unused);
            }

            private String getConstant(AnalysisContext ctx, TreePath expression) {
                switch (expression.getLeaf().getKind()) {
                    case STRING_LITERAL:
                        return (String) ((LiteralTree) expression.getLeaf()).getValue();
                    case IDENTIFIER:
                    case MEMBER_SELECT:
                       Element el = ctx.getTrees().getElement(expression);
                       if (el != null && el.getKind().isField()) {
                           Object compileTimeConstant = ((VariableElement) el).getConstantValue();
                           if (compileTimeConstant instanceof String) {
                               return (String) compileTimeConstant;
                           }
                       }
                    return null;
                    case PLUS:
                        BinaryTree binaryTree = (BinaryTree) expression.getLeaf();
                        String left = getConstant(ctx, new TreePath(expression, binaryTree.getLeftOperand()));
                        String right = getConstant(ctx, new TreePath(expression, binaryTree.getRightOperand()));

                        if(left != null && right != null) {
                            return left + right;
                        }
                }
                return null;
            }
        }.scan(file, null);
    }
}
*/
