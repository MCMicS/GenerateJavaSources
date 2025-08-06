package de.mcmics.sample;

import de.mcmics.annotation.NameCreator;

@NameCreator(number = 500)
public interface NameProvider {
    String getName();
}
