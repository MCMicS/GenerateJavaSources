package de.mcmics.sample;

import de.mcmics.annotation.NameCreator;

@NameCreator(number = 100)
public interface NameProvider {
    String getName();
}
