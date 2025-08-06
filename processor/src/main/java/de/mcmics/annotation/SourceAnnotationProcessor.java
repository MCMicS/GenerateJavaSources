package de.mcmics.annotation;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import static javax.tools.Diagnostic.Kind.NOTE;
import static javax.tools.Diagnostic.Kind.WARNING;

@SupportedAnnotationTypes({
        "de.mcmics.NameCreator"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SourceAnnotationProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        processingEnv.getMessager().printMessage(NOTE, "Generate Sources");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            final boolean allInterfacesCheck = annotatedElements.stream()
                    .allMatch(element -> element.getKind() == ElementKind.INTERFACE);
            if (allInterfacesCheck) {
                annotatedElements.forEach(this::createDummyCode);
            }else {
                processingEnv.getMessager().printMessage(WARNING, "Only works for interfaces.");
            }
        }
        return true;
    }

    private void createDummyCode(Element element) {
        final var classesToCreate = Optional.ofNullable(element.getAnnotation(NameCreator.class))
                .map(NameCreator::number).orElse(0);
        var packageName = element.getEnclosingElement().toString();
        var className = element.getSimpleName().toString();

        try {
            for (int i = 0; i < classesToCreate; i++) {
                final String classNameToCreate = className + i;
                var builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + classNameToCreate);
                try (var out = new PrintWriter(builderFile.openWriter())) {
                    if (packageName != null) {
                        out.print("package ");
                        out.print(packageName);
                        out.println(";");
                        out.println();
                    }
                    out.print("public class ");
                    out.print(classNameToCreate);
                    out.println(" implements ");
                    out.println(element.getSimpleName());
                    out.println(" {");
                    out.println("public String getName() {");
                    out.println("return ");
                    out.println("\"Name \"" + i);
                    out.println(";");
                    out.println("}");
                    out.println("}");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
