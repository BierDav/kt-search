package documentation.manual.scripting

import documentation.sourceGitRepository

val scriptingMd = sourceGitRepository.md {
    +"""
        One interesting use of kt-search is to script common operations around Elasticsearch.
        
        For this we have a companion library [kt-search-kts](https://github.com/jillesvangurp/kt-search-kts/) 
        that makes integrating `kt-search` into your `.main.kts` scripts very easy.
        
        You may find a few example scripts in the `scripts` directory of that project. To run these scripts,
        you need to have kotlin installed on your system of course.
    """.trimIndent()

    section("Example script") {
        includeMdFile("sampleScript.md")

        +"""
            The example script above adds the maven dependency and our two maven repositories
            via `@file:` directives. 
            
            The `parser.addClientParams()` extension function adds a few parameters to the 
            `kotlinx-cli` command line argument parser so we can create a `SearchClient` with the
            right parameters. You can add more parameters for your script of course. If you call the script with `-h`
            it will print all the parameters that we added:
    
            ```
                Usage: script options_list
                Options: 
                    --host, -a [localhost] -> Host { String }
                    --port, -p [9200] -> Port { Int }
                    --user -> Basic authentication user name if using with cloud hosting { String }
                    --password -> Basic authentication user name if using with cloud hosting { String }
                    --protocol [false] -> Use https if true 
                    --help, -h -> Usage info         
            ```
            
            After parsing the `args`, you can get a `SearchClient` by simply calling
            `searchClientParams.searchClient`. Kotlinx-cli's commandline parser will populate the settings.    
    
            You can use then import the client as normally. Because the client uses suspending
             functions, you have to surround your code with a `runBlocking {...}`
             
            Note, be sure to use the latest version of [kt-search-kts](https://github.com/jillesvangurp/kt-search-kts/).a
         """.trimIndent()
    }
    section("Some ideas for using kt-search on the cli") {
        +"""         
             Some ideas for using `kts` scripting with Kt-Search:
             
             - index creation and alias management
             - bulk indexing content
             - manage cluster settings
             - orchestrate rolling restarts
             - snapshot management
        """.trimIndent()
    }

    section("How to run `.main.kts` scripts") {
        +"""
            To be able to run the scripts, install kotlin 1.7 via your linux package manager, 
            home-brew, sdkman, snap, etc. There are many ways to do this.
                        
            Unfortunately, using kotlin script is a bit under-documented by Jetbrains and still has some issues.
            
            [kt-search-kts](https://github.com/jillesvangurp/kt-search-kts/) is there to get you started, of course.
            
            Limitations:

            - your script name **MUST** end in `.main.kts`
            - import handling is a bit limited especially for extension functions outside of 
              intellij. So, you may have to add the right imports manually.
            - KTS and compiler plugins are tricky. Since kt-search uses kotlinx-serialization 
              that means that defining new serializable data classes is not possible in 
              KTS unless you can add the compiler plugin. There are some workarounds for that documented 
              here: https://youtrack.jetbrains.com/issue/KT-47384. Alternatively, put your model classes in a separate library (that builds with the compiler plugin) and add a dependency to that.             
            - KTS is a bit limited in with respect to handling multi-platform dependencies. 
              Make sure to depend on the `-jvm` dependency for multi-platform dependencies 
              (like kt-search). The `kt-search-kts` library has a transitive dependency and that 
              works out fine.
            - if you add a custom repository, you also have to specify maven 
              central as a repository explicitly if you need more dependencies

            ```kotlin
            @file:Repository("https://repo1.maven.org/maven2")
            @file:DependsOn("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.5")
            @file:Repository("https://jitpack.io")
            @file:DependsOn("com.github.jillesvangurp:kt-search-kts:0.1.7")
            ```            
            - make sure to add the shebang to your script `#!/usr/bin/env kotlin` and of 
            course make it executable `chmod 755 myscript.main.kts`
            this will direct linux/mac to use kotlin to run the script with kotlin
            - intellij does not reliably reload the script context when you 
            modify the dependencies: closing and re-opening the IDE seems to work.
        """.trimIndent()

    }
}