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

}
