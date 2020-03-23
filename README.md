# FxDeployable
Standard Starting project ready to be deployed as native application as cross-platform application.
Creating Native JDK9 Application Bundles with small Runtimes




Original Source from the link above.
https://github.com/miho/NativeJDK9AppTemplate

Source code also available

Note that javaFX was removed from java 11 so doing hardwork right  now


Create a gradle project for javaFX


Add javaFX library from project structure menu in intellij idea {libraries}>java>path_to_java_fx



Also add configurations and VM options as

1. Select Application | Main from the list on the left.
2. In the VM options field, specify: --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml
Instead of %PATH_TO_FX%, specify the path to the JavaFX SDK lib directory, for example: /Users/jetbrains/Desktop/javafx-sdk-12/lib.

From <https://www.jetbrains.com/help/idea/javafx.html> 

Gradle File  {Install gradle for command line options}
plugins{
id'java'
}

group'org.example'
version'1.0-SNAPSHOT'

sourceCompatibility=1.8

repositories{
mavenCentral()
}

dependencies{

}

defjdk=System.getProperty("java.home")
defmoduleName="templateFX"
defmainClass='templateFX.Main'


//jarfiletask(weaddthemainclassandclasspathattributestothemanifest)
jar{
manifest{
attributes(
'Main-Class':mainClass,
)
}
}

//createsapplicationbundle(executable+runtime)
taskjavaPackager(type:Exec,dependsOn:assemble){

workingDirproject.projectDir
commandLine=[
'javapackager',
'-deploy',
'-nosign',
'-native','image',
'-outdir',"${buildDir}/distribution",
'-outfile',project.name,
'-name',project.name,
'-appclass',mainClass,
'-p',"${jdk}/jmods${File.pathSeparator}${buildDir}/libs",
'--add-modules',moduleName,
'-m',"$moduleName/$mainClass"
]
}

//removesbloatedruntimecreatedbyjavapackager
taskcleanPackagerRuntime(dependsOn:javaPackager){
doLast(){
FileruntimeFile=newFile("${buildDir}/distribution/${project.name}/runtime")
runtimeFile.deleteDir()
System.out.println("deletingbloatedruntimein"+runtimeFile)
}
}

//createsareplacementruntimeviajlinkcommand(muchsmallerthanjpackager)
taskcreateFinalAppBundle(type:Exec,dependsOn:[cleanPackagerRuntime]){

workingDirproject.projectDir
commandLine=[
'jlink',
'-p',"${jdk}/jmods${File.pathSeparator}${buildDir}/libs",
'--add-modules',moduleName,
'--strip-debug',
'--no-header-files',
'--no-man-pages',
'--strip-native-commands',
"--vm=server",
"--compress=2",
'--output',"${buildDir}/distribution/${project.name}/runtime"
]

doLast{
System.out.println("Application'${project.name}'packaged.")
System.out.println("->location:${buildDir}/distribution/${project.name}/")
}
}






Module-info-Folder

moduletemplateFx{

requiresjavafx.base;
requiresjavafx.graphics;
requiresjavafx.controls;

exportstemplateFx;
}



Used JDK 9 and everything seems to be working fine.
