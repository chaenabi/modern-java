/*
package com.company.javac;

import com.company.finalKeyWordTest.Final;
import org.openide.util.lookup.ServiceProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.swing.text.Utilities;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("*")
@ServiceProvider(service = Processor.class)
public class FinalRule extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(Element e : roundEnv.getRootElements()) {
            if(!e.getKind().isClass()) {
                continue;
            }

            TypeElement clazz = (TypeElement) e;

            OUTER: for (TypeMirror superInterface : clazz.getInterfaces()) {
                Element superInterfaceEl =
                        processingEnv.getTypeUtils().asElement(superInterface);
                AnnotationMirror finalAnnotation =
                        Utilities.findAttachedAnnotation(processingEnv, superInterfaceEl, Final.class.getName()); // ----

                if(finalAnnotation == null) continue;
                TypeMirror[] allowedImplementors = TypeMirror(processingEnv, finalAnnotation, "implementors", TypeMirror[].class); // ---

                  for(TypeMirror allowedImplementor : allowedImplementors) {
                    if(clazz.equals(processingEnv.getTypeUtils().asElement(allowedImplementor))) { //---
                        continue OUTER;
                    }
                }
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Cannot implement: " + superInterface + ".API, not listed implementor. ", clazz);
            }
        }
        return false;
    }
}
*/
