# Gradle Project Writer Plugin
Writes a build.gradle file from a Project object. Primary use of this is to assist in a gradle migration project I'm
working on, but it could also be used to support tests that use the ProjectBuilder.

The first thing this will support will be Groovy DSL (as that's supported by Gradle Init). I'll consider kotlin support
at a later time.
