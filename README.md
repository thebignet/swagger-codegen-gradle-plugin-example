swagger-codegen-gradle-plugin-example
=====================================

[![Build Status](https://travis-ci.org/thebignet/swagger-codegen-gradle-plugin.svg?branch=master)](https://travis-ci.org/thebignet/swagger-codegen-gradle-plugin-example)
https://github.com/thebignet/swagger-codegen-gradle-plugin-example

This repository shows how to use swagger codegen with Gradle using a Gradle task.

Include swagger-codegen
-----------------------

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath('io.swagger:swagger-codegen:2.2.3')
    }
}
```

Change the version with the one you'd like to use

Create task generateApi
-----------------------

Here is an example of how to create the **generateApi** task.

Use `config` to pass parameters as in the [documentation](https://github.com/swagger-api/swagger-codegen)

```groovy
import io.swagger.codegen.config.CodegenConfigurator
import io.swagger.codegen.DefaultGenerator

def swaggerInput = "petstore.json"
def swaggerOutputDir = file('build/swagger')
task generateApi {
    inputs.file(swaggerInput)
    outputs.dir(swaggerOutputDir)
    doLast {
        def config = new CodegenConfigurator()
        config.setInputSpec(swaggerInput)
        config.setOutputDir(swaggerOutputDir.path)
        config.setLang('java')
        config.setAdditionalProperties([
                'invokerPackage': 'io.swagger.petstore.client',
                'modelPackage'  : 'io.swagger.petstore.client.model',
                'apiPackage'    : 'io.swagger.petstore.client.api',
                'dateLibrary'   : 'java8'
        ])
        config.setImportMappings([
                'Dog': 'io.swagger.petstore.client.model.Dog'
        ])
        new DefaultGenerator().opts(config.toClientOptInput()).generate()
    }
}

clean.doFirst {
    delete(swaggerOutputDir)
}
```

`swaggerInput` can also be a URL, but in this case, it is not possible to use Gradle's `input` property for [incremental builds](https://docs.gradle.org/current/userguide/more_about_tasks.html#sec:up_to_date_checks).


Source Set Swagger
------------------

Here is an example to create a swagger source set for the java language, where sources are in `src/main/java` of the `swaggerOutputDir`

```groovy
configurations {
    swagger
}

sourceSets {
    swagger {
        compileClasspath = configurations.swaggerCompile
        java {
            srcDir file("${project.buildDir.path}/swagger/src/main/java")
        }
    }
    main {
        compileClasspath += swagger.output
        runtimeClasspath += swagger.output
    }
    test {
        compileClasspath += swagger.output
        runtimeClasspath += swagger.output
    }
}
```

Add theses to make sure build tasks depend on swagger tasks.
```groovy
compileSwaggerJava.dependsOn generateApi
classes.dependsOn swaggerClasses
compileJava.dependsOn compileSwaggerJava
```

Generate Swagger Sources
------------------------

In order to generate Swagger source code, launch the following task.

- generateApi : generate Swagger code

