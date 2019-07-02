package com.github.slopeoak.projectwriter

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

class WriteExternalDependenciesSpec extends Specification {

    @Rule
    TemporaryFolder temporaryFolder

    @Unroll
    def "Writes a dependency with scope '#scope' and dependency body '#dependency'"() {
        given: 'there is a project'
            def project = ProjectBuilder.builder().build()

        and: 'the project has the dependency'
            project.configurations.create(scope)
            project.dependencies.add(scope, dependency)

        when: 'write is called with the project instance'
            ProjectWriter.write(project, temporaryFolder.root)

        then: 'there is a new build.gradle file in the target folder'
            def buildFile = new File(temporaryFolder.root, 'build.gradle')

        and: 'the build.gradle file matches the expected body'
            buildFile.text == new File("src/integrationTest/resources/dependencies/external/scopes/$expected").text

        where:
            scope            | dependency                                                      || expected
            'compile'        | 'com.github.slopeoak:gradle-plugin:0.0.1'                       || 'compile.gradle'
            'testCompile'    | 'com.github.slopeoak:library:0.0.1'                             || 'testCompile.gradle'
            'implementation' | 'org.apache.commons:commons-lang3:3.8.1'                        || 'implementation.gradle'
            'integTest'      | 'some.other.library:with-an-artifact-id:and.a.version-SNAPSHOT' || 'integTest.gradle'
    }

    @Unroll
    def "Writes an external dependency with the targetConfiguration set to #targetConfig"() {
        given: 'there is a project'
            def project = ProjectBuilder.builder().build()

        and: 'the project has the dependency'
            project.configurations.create('compile')
            def dep = project.dependencies.add('compile', shortForm)
            dep.targetConfiguration = targetConfig

        when: 'write is called with the project instance'
            ProjectWriter.write(project, temporaryFolder.root)

        then: 'there is a new build.gradle file in the target folder'
            def buildFile = new File(temporaryFolder.root, 'build.gradle')

        and: 'the build.gradle file matches the expected body'
            buildFile.readLines().any {
                it ==~ /(.*)$expected(.*)/
            }

        where:
            group                 | name              | version    | targetConfig
            'com.github.slopeoak' | 'project-writer'  | '0.0.2'    | 'all'
            'com.github.slopeoak' | 'project-library' | '1.2.3'    | 'sources'
            'com.github.slopeoak' | 'gradle-init'     | 'whatever' | 'trevor'

            shortForm = "$group:$name:$version"
            expected = "compile\\(group: '$group', name: '$name', version: '$version', targetConfiguration: '$targetConfig'\\)"
    }

    @Unroll
    def "Writes an external dependency as #expected when there is no targetConfiguration"() {
        given: 'there is a project'
            def project = ProjectBuilder.builder().build()

        and: 'the project has the dependency'
            project.configurations.create('compile')
            project.dependencies.add('compile', shortForm)

        when: 'write is called with the project instance'
            ProjectWriter.write(project, temporaryFolder.root)

        then: 'there is a new build.gradle file in the target folder'
            def buildFile = new File(temporaryFolder.root, 'build.gradle')

        and: 'the build.gradle file matches the expected body'
            buildFile.readLines().any {
                it ==~ /(.*)$expected(.*)/
            }

        where:
            group                 | name              | version
            'com.github.slopeoak' | 'project-writer'  | '0.0.2'
            'com.github.slopeoak' | 'project-library' | '1.2.3'
            'com.github.slopeoak' | 'gradle-init'     | 'whatever'

            shortForm = "$group:$name:$version"
            expected = "compile '$group:$name:$version'"
    }

    @Unroll
    def "#targetConfig targetConfiguration writes short form"() {
        given: 'there is a project'
            def project = ProjectBuilder.builder().build()

        and: 'the project has the dependency'
            project.configurations.create('compile')
            def dep = project.dependencies.add('compile', shortForm)
            dep.targetConfiguration = targetConfig

        when: 'write is called with the project instance'
            ProjectWriter.write(project, temporaryFolder.root)

        then: 'there is a new build.gradle file in the target folder'
            def buildFile = new File(temporaryFolder.root, 'build.gradle')

        and: 'the build.gradle file matches the expected body'
            buildFile.readLines().any {
                it ==~ /(.*)$expected(.*)/
            }

        where:
            group                 | name              | version | targetConfig
            'com.github.slopeoak' | 'project-writer'  | '0.0.2' | 'jar'
            'com.github.slopeoak' | 'project-library' | '1.2.3' | null

            shortForm = "$group:$name:$version"
            expected = "compile '$group:$name:$version'"
    }
}
