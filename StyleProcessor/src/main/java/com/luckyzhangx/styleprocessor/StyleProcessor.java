package com.luckyzhangx.styleprocessor;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.luckyzhangx.styleannos.Style;
import com.luckyzhangx.styleannos.StyleFamily;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class StyleProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(
                Style.class.getCanonicalName()
        );
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotationMirror, ExecutableElement executableElement, String s) {
        return super.getCompletions(element, annotationMirror, executableElement, s);
    }

    @Override
    protected synchronized boolean isInitialized() {
        return super.isInitialized();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "begin style processing");

        for (TypeElement typeElement : set) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(StyleFamily.class);
            for (Element element : elements) {
                if (element.getKind() == ElementKind.ANNOTATION_TYPE) {
                    Set<? extends Element> styleFamilies = roundEnvironment.getElementsAnnotatedWith(((TypeElement) element));
                    for (Element element1 : styleFamilies) {

                        TypeSpec helloWorld = TypeSpec.classBuilder(element1.getSimpleName().toString())
                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                .build();

                        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                                .build();
                        try {
                            javaFile.writeTo(processingEnv.getFiler());
                        } catch (Exception e) {

                        }
                    }
                }

            }

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "style:" + typeElement);
        }


        return true;
    }
}
