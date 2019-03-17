package com.genric.library.health;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class HealthAggregatorTest {

    HealthAggregator healthAggregator;

    @Before
    public void setUp(){
        healthAggregator = new HealthAggregator();
    }

    @Test
    public void setResource() {
        healthAggregator.setResource("db", true);
        Assert.assertTrue(healthAggregator.isHealthy());
    }

    @Test
    public void setReouesesWithMultipleThread(){
        int threads = 10;
        ExecutorService service =
                Executors.newFixedThreadPool(threads);
        Collection<Future<Integer>> futures =
                new ArrayList<>(threads);
        for (int t = 0; t < threads; ++t) {
            final String resource = String.format("Resource #%d", t);
            boolean flag = t%2 == 0;
            service.submit(() -> healthAggregator.setResource(resource, flag));
        }
        Assert.assertFalse(healthAggregator.isHealthy());
    }

    @Test
    public void getStatusWithMultipleResources(){
        healthAggregator.setResource("db", true);
        healthAggregator.setResource("redis", false);
        Assert.assertFalse(healthAggregator.isHealthy());
    }

    @Test
    public void isHealthy() {
        Assert.assertFalse(healthAggregator.isHealthy());
    }
}