package de.mcmics;

import de.mcmics.annotation.NameCreator;

@NameCreator(number = 2)
public interface NameProvider {
    String getName();
}
