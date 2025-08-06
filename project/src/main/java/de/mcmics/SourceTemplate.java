package de.mcmics;

public class SourceTemplate {
    public String hello(NameProvider nameProvider) {
        return "Hello " + nameProvider.getName();
    }
}
