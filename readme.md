# Gradle Project Writer
Writes build.gradle files from [Project](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html) instances. One use case may
be that you're writing test data with the 
[Project Builder](https://docs.gradle.org/current/javadoc/org/gradle/testfixtures/ProjectBuilder.html) and decide you want to write it to 
a file. Another use case is that you're modifying the state of your project and would like to persist those changes.

This project aims to create a library (useful for writing test data to file) and a plugin (useful for persisting project changes to file)
for the Groovy DSL.

This plugin can be found in the [Gradle plugins repository](https://plugins.gradle.org/plugin/com.github.slopeoak.project-writer)

## About the project
The project has 2 subprojects:
* [Library](https://github.com/slopeoak/project-writer/tree/master/library)
* [Plugin](https://github.com/slopeoak/project-writer/tree/master/plugin)

The library uses [Mustache](https://mustache.github.io/) to template the project files. 

The code is written in Groovy.
