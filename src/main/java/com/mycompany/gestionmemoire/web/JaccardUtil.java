package com.mycompany.gestionmemoire.web;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public final class JaccardUtil {
  private JaccardUtil() {}

  public static Set<String> motsCles(String texte) {
    if (texte == null || texte.isBlank()) {
      return Set.of();
    }

    String normalise =
        Normalizer.normalize(texte.toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "");
    return Arrays.stream(normalise.split("[^\\p{L}\\p{N}]+"))
        .filter(mot -> !mot.isBlank())
        .collect(Collectors.toCollection(HashSet::new));
  }

  public static double calculer(String premier, String second) {
    Set<String> ensembleA = motsCles(premier);
    Set<String> ensembleB = motsCles(second);
    Set<String> union = new HashSet<>(ensembleA);
    union.addAll(ensembleB);

    if (union.isEmpty()) {
      return 0.0;
    }

    Set<String> intersection = new HashSet<>(ensembleA);
    intersection.retainAll(ensembleB);
    return (double) intersection.size() / union.size() * 100.0;
  }
}
