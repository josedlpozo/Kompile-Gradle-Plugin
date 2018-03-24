# Kompile Gradle Plugin
[![CircleCI](https://circleci.com/gh/josedlpozo/Kompile-Gradle-Plugin.svg?style=svg)](https://circleci.com/gh/josedlpozo/Kompile-Gradle-Plugin)

Kompile Gradle Plugin is a [Gradle Plugin](https://docs.gradle.org/current/userguide/custom_plugins.html) that calculate how much time have you been kompiling and send it to [Kompile](https://github.com/josedlpozo/Kompile). 

This plugin picks your alias, email and project in which you are working and send your build times to your Kompile server.
 
 ## How it works?
 
 This plugin needs that your project is tracked in a git repository, and also you have defined your user name and email in the git profile. Also, in order to pick up the project name, we need you to have your remote git url named as 'origin'(git uses this as default).
 If you want to try if this plugin will work for you, just try to run these commands:
 - Project name: ```git config --get remote.origin.url | head -n1 | sed 's/.*\///' | sed 's/\.git//'```
 - User name: ```git config --get user.name```
 - User email: ```git config --get user.email```

 If these commands print an output in your command line, you're right to use Kompile Gradle Plugin :smiley:, other way please open an issue.
 
 ## Installation
 Apply the plugin in your ``build.gradle``:
 
 ```groovy
 
 buildscript {
   repositories {
     maven { url 'https://jitpack.io' }
   }
   dependencies {
     classpath 'com.github.josedlpozo:Kompile-Gradle-Plugin:0.0.1'
   }
 }
 
 apply plugin: 'com.josedlpozo.kompile'
 
 ```
 
 ### Configuration
 
 You just need to indicate the Kompile server host:
 
 ```groovy
 apply plugin: 'com.josedlpozo.kompile'
 
 kompile {
     host = "http://kompile.host"
 }
 ```