package de.mcmics;

import de.mcmics.annotation.SourceCreator;

public class SourceTemplate {
    public String hello(NameProvider nameProvider) {
        return "Hello " + nameProvider.getName();
    }
}
