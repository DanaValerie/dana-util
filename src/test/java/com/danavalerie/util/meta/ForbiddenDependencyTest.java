package com.danavalerie.util.meta;

import com.danavalerie.util.reflect.ClasspathUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ForbiddenDependencyTest {

    @Test
    public void testNoForbiddenDependenciesAreFound() throws IOException {
        final Set<String> forbiddenEntriesFound = new HashSet<>();
        // The 'dana-util' module should have a minimalist set of dependencies.
        final List<URI> systemClassPathEntries = ClasspathUtil.getSystemClassPathEntries();
        for (final URI systemClassPathEntry : systemClassPathEntries) {
            for (final String className : ClasspathUtil.findAllClasses(systemClassPathEntry)) {
                assertThat(className).doesNotStartWith("org.springframework.");
            }
        }
        assertThat(forbiddenEntriesFound).isEmpty();
    }

}
