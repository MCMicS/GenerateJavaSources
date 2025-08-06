package de.mcmics.sample;

import de.mcmics.annotation.NameCreator;

@NameCreator(number = 2_000)
public interface NameProvider {
    String getName();
}
