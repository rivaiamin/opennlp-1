package opennlp.tools.namefind;

import java.util.List;


/**
 * Generates features for different for the class of the token.
 */
public class TokenClassFeatureGenerator implements FeatureGenerator {

  private static final String TOKEN_CLASS_PREFIX = "wc";

  public void createFeatures(List features, String[] tokens, int index) {
    String wordClass = FeatureGeneratorUtil.tokenFeature(tokens[index]);

    features.add(TOKEN_CLASS_PREFIX + "=" + wordClass);
  }
}