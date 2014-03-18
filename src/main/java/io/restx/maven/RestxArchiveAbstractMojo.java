package io.restx.maven;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import restx.core.shell.RestxArchive;

import java.io.File;
import java.nio.file.Path;

/**
 * @author fcamblor
 */
public abstract class RestxArchiveAbstractMojo extends AbstractMojo {


    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject project;

    @Parameter( defaultValue = "${project.build.directory}" )
    protected File targetDirectory;

    protected Path getRestxArchivePath(){
        Path restxArchive = targetDirectory.toPath().resolve(project.getArtifact().getArtifactId()+"-"+project.getVersion()+"-"+getClassifier()+"."+getType());
        return restxArchive;
    }

    protected String getType(){
        return "jar";
    }

    protected String getClassifier(){
        return "restx";
    }

}
