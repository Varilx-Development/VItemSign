package de.varilx.vitemsign.loader;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

/**
 * Project: VHomes
 * Package: de.varilx.development.homes.loader
 * <p>
 * Author: ShadowDev1929
 * Created on: 10.05.2025
 */
@SuppressWarnings({
        "unused",
        "UnstableApiUsage"
})
public class VItemSignLoader implements PluginLoader {

    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addRepository(new RemoteRepository.Builder("central", "default", "https://repo1.maven.org/maven2/").build());
        resolver.addRepository(new RemoteRepository.Builder("papermc", "default", "https://repo.papermc.io/repository/maven-public/").build());
        resolver.addRepository(new RemoteRepository.Builder("sonatype-public", "default", "https://oss.sonatype.org/content/groups/public/").build());
        resolver.addRepository(new RemoteRepository.Builder("varilx-development", "default", "https://reposilite.varilx.de/Varilx").build());

        resolver.addDependency(new Dependency(new DefaultArtifact("de.varilx:base-api:1.3.2"), null));

        classpathBuilder.addLibrary(resolver);
    }
}
