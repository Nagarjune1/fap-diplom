# fapapp

This project implements some techniques based on fuzzy automata. This document describes some basic info.

## Structure

This project contains few submodules, each implementing particular kind of automaton.

Each of such submodule also contains implementation of some choosen real-world problems described in literature. 

The general functionalities are located within the core module.

## Requirements

This project is implemented in Java. Required version is 1.8 and newer. To build is recommended to use Apache Maven.

No other libraries, modules or tools required.  

## Build

This project is maven friendly. That means, build can be performed using following command within the root directory of the project (same as this file) (or in root of particular module as well):

    mvn clean instal
    
For non-maven users, this will perform clean, build, tests and instalation in so-called local repository. To simply generate JAR packages run `mvn clean package` (packages may occur within the target folder). 

To generate Eclipse IDE project try `mvn eclipse:eclipse`.  generate javadoc type `mvn javadoc:javadoc -DadditionalJOption=-Xdoclint:none`. If there are some troubles with tests, flag  `-DskipTests` skips them.


## Running

This project can be used as a software library as well as standalone app. Each module (except the core one) contains bulk of executable (main) classes. They can be invoked by plain old `java -cp ...` way, but it is preferred to use maven. Run following command in the root folder of particular module:

    mvn exec:java -Dexec.mainClass="cz.upol.fapapp.xxx.YYYY" -Dexec.args="foo bar baz"

where `cz.upol.fapapp.xxx.YYYY` points to the required main class and "foo bar baz" are the program arguments.

Each of such classes understands "-h" or "--help" flag, try them and explore what parameters each main class requires or which are optional.  

## Samples

Each module is equipped with bulk of tesing data. Theese can be found in the `test-data` subfolder within the each module's root folder. Some modules also contains some helping test scripts written in Bash.

## More info

There are READMEs within each module. The code contains as much of javadoc as possible. To see how to use some classes take a look into tests, it might help. 

At the last point, try to look into theoretical part.