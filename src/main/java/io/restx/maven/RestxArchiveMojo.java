package io.restx.maven;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProjectHelper;
import restx.core.shell.RestxArchive;

import java.io.File;
import java.nio.file.Path;

/**
 * @author fcamblor
 */
@Mojo(name = "archive", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true)
public class RestxArchiveMojo extends RestxArchiveAbstractMojo {

    private static final String[] DEFAULT_EXCLUDES = new String[]{ "target", "tmp", "logs" };

    @Parameter
    private String[] excludes;

    @Parameter( defaultValue = "${project.build.outputDirectory}" )
    protected File targetClassesDirectory;

    @Component
    private MavenProjectHelper projectHelper;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Path restxArchive = getRestxArchivePath();
        new RestxArchive(restxArchive).pack(project.getBasedir().toPath(), targetClassesDirectory.toPath(), Arrays.asList(getCombinedExcludes()));

        projectHelper.attachArtifact(project, getType(), getClassifier(), getRestxArchivePath().toFile());
    }

    private String[] getCombinedExcludes( ) {
        if(this.excludes == null || this.excludes.length == 0) {
            return DEFAULT_EXCLUDES;
        }

        return excludes;
    }
}
