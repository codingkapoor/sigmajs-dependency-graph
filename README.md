# smf-dependency-graph
An attempt to build dependency graphs using simgajs javascript library.

# Objective
The object is to create dependency graph of all the processes managed by [SMF framework](http://www.oracle.com/technetwork/articles/servers-storage-admin/intro-smf-basics-s11-1729181.html) on a Solaris machine using a javascipt library called as [SigmaJs](http://sigmajs.org).

# Algorithm
This algorithm attempts to create a list of nodes and edges as a json string i.e., in a format acceptable by simgajs library provided a list of processes and there dependants in a particular format as specified below.

```
read the input file that maintains list of processes and their dependants labels in a particular format
iterate through the list of source/target node labels until end of file is met
	create a source node object with the source node label
	check if the source node object already exists in the nodes list, if yes retrieve and replace the newly created source node object with it
	iterate through the list of source/target node labels until blank line is met
		create target node object with the target node label
		check if the target node object already exists in the nodes list, if yes retrieve and replace the newly created target node object with it
		create edge object with the source and target nodes
populate graph object with the nodes and edges list
marshall the graph object into a json string in an output file
```

###Input file format
Maintains a list of processes and their dependants.
```
node a
node b
node c

node d
node e
node f

node f
node g
```

###Output file format
The description regarding this format can be found at [SigmaJs](http://sigmajs.org).
```
{
  "nodes": [
    {
      "id": "n0",
      "label": "A node",
      "x": 0,
      "y": 0,
      "size": 3
    },
    {
      "id": "n1",
      "label": "Another node",
      "x": 3,
      "y": 1,
      "size": 2
    }
  ],
  "edges": [
    {
      "id": "e0",
      "source": "n0",
      "target": "n1"
    }
  ]
}
```


Processes and there dependants are separated by a blank line where the first and the subsequent lines represents processes and there dependants respectively. In the algorithm they are represented as source node and the target node where source nodes are processes and target nodes are there dependants.
```
node a <source node>
node b <target node>
node c <target node>

node d <source node>
node e <target node>
node f <target node>

node f <source node>
node g <target node>
```


#####Notes
- The best data structure to represent a dependency graph is DAG i.e., directed acyclic graph.
- Algorithm still needs to support input files that lists processes and there dependencies.
