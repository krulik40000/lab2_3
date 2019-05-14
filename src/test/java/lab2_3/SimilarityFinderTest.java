package lab2_3;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.iis.mto.search.SearchResult;
import edu.iis.mto.similarity.SimilarityFinder;
import edu.iis.mto.search.SequenceSearcher;

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
    public void testJaccardSimilarityWithEmptySeq() {
        similarityFinder = new SimilarityFinder((key, seq) -> SearchResult.builder()
                                                                          .build());
        Assert.assertThat(similarityFinder.calculateJackardSimilarity(emptySeq, emptySeq), is(equalTo(1.0d)));
    }

    @Test
    public void testJaccardSimilarityWithDifferentSeq() {
        similarityFinder = new SimilarityFinder((key, seq) -> SearchResult.builder()
                                                                          .withFound(false)
                                                                          .build());
        Assert.assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2), is(equalTo(0d)));
    }

    @Test
    public void testJaccardSimilarityWithSameSeq() {
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

    @Test
    public void testJaccardSimilarityWithPartSameSeq() {
        similarityFinder = new SimilarityFinder((key, seq) -> {
            if (key == seq[0] || key == seq[1] || key == seq[2])
                return SearchResult.builder()
                                   .withFound(true)
                                   .build();
            return SearchResult.builder()
                               .withFound(false)
                               .build();
        });
        Assert.assertThat(0.4d, is(equalTo(similarityFinder.calculateJackardSimilarity(seq3, seq1))));
    }

    @Test
    public void testJaccardSimilarityWithCountingDoubler() {
        SequenceSearcherDoubler sequenceSearcherDoubler = new SequenceSearcherDoubler();
        similarityFinder = new SimilarityFinder(sequenceSearcherDoubler);
        similarityFinder.calculateJackardSimilarity(seq3, seq1);
        Assert.assertThat(sequenceSearcherDoubler.getCounter(), is(equalTo(seq3.length)));
    }
}

class SequenceSearcherDoubler implements SequenceSearcher {

    private int counter = 0;

    @Override
    public SearchResult search(int key, int[] seq) {
        this.counter++;
        return SearchResult.builder()
                           .build();
    }

    int getCounter() {
        return counter;
    }
}
