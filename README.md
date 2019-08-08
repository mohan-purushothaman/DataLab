# DataLab
----------

A simplified visual solution to bridge different data systems

Design data processing tasks visually as graph by using basic constructs Reader, Processor, Writer and Condition . These tasks can be executed and execution progress would be shown visually.  

##Basic Constructs
>### Reader  
Reader generate or fetch data from a data system. Reader is the starting point for any task and only once present for any task. It does not require any input data and generate data using a data system.

>### Processor 
Processor manipulate given data by interacting with a data system. It takes input data and generate a output data.

>### Writer
Writer store/write given data into a data system. Writer takes input data and do not allow any more dataflow.

>### Condition 
Condition helps to introduce branching in data flow using given data. Data flow follows true branch if condition is evaluated to TRUE else false branch is followed.

***

##Connectors
DataLab will define connectors, which provide above basic constructs for those systems for users to interact with visually.
These connectors implement runtime logics related to those data systems and will produce or consume data as runtime execution unit by communicating with that system.

As part of DataLab version 1.0-alpha-2 following connectors are added.


| Data System                   | Reader        | Processor  | Writer      | Condition |
|---|:---:|:---:|:---:|:---:|
|Delimited Files (CSV, TSV ...) |&#9989;             |            |&#9989;           |           |
|Excel                          | &#9989;             |            |&#9989;           |           |
|SQL database                   | &#9989;             |&#9989;            |&#9989;           |           |
|Web Resources                  |                     |&#9989;            |&#9989;           |           |
|Simplified Java Connector      |                     |&#9989;            |&#9989;           |&#9989;    |
|Advanced Java Connector        | &#9989;             |&#9989;            |&#9989;           |&#9989;    |
