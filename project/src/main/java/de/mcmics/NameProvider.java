package de.mcmics;

import de.mcmics.annotation.NameCreator;

@NameCreator(number = 10_000)
public interface NameProvider {
    String getName();
}
