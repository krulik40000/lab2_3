package lab2_3;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.iis.mto.search.SearchResult;
import edu.iis.mto.similarity.SimilarityFinder;
import org.junit.Assert;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimilarityFinderTest {

    private SimilarityFinder similarityFinder;
    private int emptySeq[] = new int[0];
    private int seq1[] = {1, 2, 3};
    private int seq2[] = {4, 5, 6};
    private int seq3[] = {1, 3, 4, 6};

    @Test
    public void testJaccardSimilarityEmptySeq() {
        similarityFinder = new SimilarityFinder((key, seq) -> SearchResult.builder()
                                                                          .build());
        Assert.assertThat(similarityFinder.calculateJackardSimilarity(emptySeq, emptySeq), is(equalTo(1.0d)));
    }

    @Test
    public void testJaccardSimilarityDifferentSeq() {
        similarityFinder = new SimilarityFinder((key, seq) -> SearchResult.builder()
                                                                          .withFound(false)
                                                                          .build());
        Assert.assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2), is(equalTo(0d)));
    }

    @Test
    public void testJaccardSimilaritySameSeq() {
        similarityFinder = new SimilarityFinder((key, seq) -> {
            if (key == seq[0] || key == seq[1] || key == seq[2] || key == seq[3])
                return SearchResult.builder()
                                   .withFound(true)
                                   .build();
            return SearchResult.builder()
                               .withFound(false)
                               .build();
        });
        Assert.assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq1), is(equalTo(1d)));
    }
}
