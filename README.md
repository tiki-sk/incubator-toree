IBM Spark Kernel
================

A simple Scala application to connect to a Spark cluster and provide a generic,
robust API to tap into various Spark APIs. Furthermore, this project intends to
provide the ability to send both packaged jars (standard jobs) and code
snippets (with revision capability) for scenarios like IPython for dynamic
updates. Finally, the kernel is written with the future plan to allow multiple
applications to connect to a single kernel to take advantage of the same
Spark context.

Building from Source
--------------------

To build the kernel from source, you can use the provided sbt launcher script
to compile the Scala code:

    sbt/sbt compile
    
Usage Instructions
------------------

The IBM Spark Kernel is provided as a series of jars, which can be executed on
their own or as part of the launch process of an IPython notebook.

The following command line options are available:

* --profile <file> - the file to load containing the ZeroMQ port information
* --help - displays the help menu detailing usage instructions
* --master - location of the Spark master (defaults to local[*])

Additionally, ZeroMQ configurations can be passed as command line arguments

* --ip <address>
* --stdin-port <port>
* --shell-port <port>
* --iopub-port <port>
* --control-port <port>
* --heartbeat-port <port>

Ports can also be specified as Environment variables:

* IP
* STDIN_PORT
* SHELL_PORT
* IOPUB_PORT
* CONTROL_PORT
* HB_PORT

Packing the kernel
------------------

We are utilizing [xerial/sbt-pack](https://github.com/xerial/sbt-pack) to
package and distribute the Spark Kernel.

You can run the following to package the kernel, which creates a directory in
_kernel/target/pack_ contains a Makefile that can be used to install the kernel
on the current machine:

    sbt/sbt kernel/pack
    
To install the kernel, run the following:
    
    cd kernel/target/pack
    make install
    
This will place the necessary jars in _~/local/kernel/current/lib_ and provides
a convient script to start the kernel located at 
_~/local/kernel/current/bin/sparkkernel_.

Development Instructions
------------------------

You must have *SBT 0.13.5* installed. From the command line, you can attempt to
run the project by executing `sbt/sbt kernel/run <args>` from the root 
directory of the project. You can run all tests using `sbt/sbt test` (see
instructions below for more testing details).

For IntelliJ developers, you can attempt to create an IntelliJ project
structure using `sbt/sbt gen-idea`.

Running tests
-------------

There are three levels of test in this project:

1. Unit - tests that isolate a specific class/object/etc for its functionality

2. Integration - tests that illustrate functionality between multiple
   components

3. System - tests that demonstrate correctness across the entire system

4. Scratch - tests isolated in a local branch, used for quick sanity checks,
   not for actual inclusion into testing solution

To execute specific tests, run sbt with the following:

1. Unit - `sbt/sbt unit:test`

2. Integration - `sbt/sbt integration:test`

3. System - `sbt/sbt system:test`

4. Scratch - `sbt/sbt scratch:test`

To run all tests, use `sbt/sbt test`!

The naming convention for tests is as follows:

1. Unit - test classes end with _Spec_
   e.g. CompleteRequestSpec
    * Placed under _com.ibm.spark_

2. Integration - test classes end with _SpecForIntegration_
   e.g. InterpreterWithActorSpecForIntegration
    * Placed under _integration_

3. System - test classes end with _SpecForSystem_
   e.g. InputToAddJarForSystem
    * Placed under _system_

4. Scratch
    * Placed under _scratch_
