
package com.company.jsonFormat;

import com.google.auto.service.AutoService;
import com.google.gson.Gson;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.company.jsonFormat.ToStringJson")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class ToStringJsonProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) return false;

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ToStringJson.class);
        for (Element element : elements) {
            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "this annotation is appended on class only.");
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "process: " + element.getSimpleName());
            }

            TypeElement typeElement = (TypeElement) element;
            ClassName className = ClassName.get(typeElement);

            MethodSpec toString = MethodSpec.methodBuilder("toString")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement(String.format("return \"%s\"", new Gson().toJson(className)))
                    .returns(String.class)
                    .build();

            TypeSpec name = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(toString)
                    .build();

            Filer filer = processingEnv.getFiler();
            try {
                JavaFile.builder(className.packageName(), name)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + e);
            }
        }

        return true;
    }
}

@ToStringJson
class User {
    private String name;
    private Integer age;
}

class JsonTest {
    public static void main(String[] args) {
        System.out.println(new User());
    }
}