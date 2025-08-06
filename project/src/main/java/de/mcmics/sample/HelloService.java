package de.mcmics.sample;

public class HelloService {
    public String hello(NameProvider nameProvider) {
        return "Hello " + nameProvider.getName() + "!";
    }
}
