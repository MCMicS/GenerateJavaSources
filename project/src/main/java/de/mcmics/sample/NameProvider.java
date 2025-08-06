package de.mcmics.sample;

import de.mcmics.annotation.NameCreator;

@NameCreator(number = 10_000)
public interface NameProvider {
    String getName();
}
