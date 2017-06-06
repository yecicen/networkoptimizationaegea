# networkoptimizationaegea
network optimization on Aegean Cities with Dijsktra's shorthest path algorithm

##Problem Definition 
Aegean Region has eight cities and there is distances between them. We as a citizen of İzmir city, we wonder that what is the shortest distance for all those cities from İzmir. Therefore we created network that represents cities of Aegean Region. Then we applied Dijsktra’s Shortest Path Algorithm for our network. The solution as follows:
[ izmir     manisa      aydın     uşak         denizli       muğla     afyon      kütahya ]
[ 0*             ∞              ∞           ∞               ∞              ∞             ∞             ∞  ] 
d(2)=min {∞,d(1)+c12} =  39km*
d(3)=min {∞,d(1)+c13} = 180km*
[ 0*            39*          180*        ∞               ∞              ∞             ∞             ∞  ]
d(5)=min {∞,d(1)+c15} = min{∞, 0 + 255} = 255km
d(5)=min {255,d(2)+c25} = min {255, 39 + 233} = 255km
d(5)=min {255,d(3)+c35} = min {255, 180+103} = 255km*
[ 0*            39*           180 *       ∞	       255*           ∞             ∞             ∞  ]
d(4)=min {∞,d(1)+c14} = min {∞, 0+216 } = 216km
d(4)=min {216, d(5)+c54} = min { 216, 255+169 } =216km
d(4)=min {216,d(2)+c24} = min {216, 39+194} =216km*
[ 0*            39*           180*      216*       255*             ∞             ∞             ∞  ]
d(6)=min {∞,d(3)+c36} = min {∞, 180+136} = 316km
d(6)=min {316,d(5)+c56 } = min {316, 255+113} = 316km*
 [ 0*            39*            180*        216*        255*         316*         ∞            ∞  ]
d(7)=min {∞,d(4)+c47} = min {∞, 216+111} =327km
d(7)=min {327,d(5)+c57} = min {327, 255+229 } =327km*
[ 0*            39*            180*        216*        255*         316*        327*          ∞  ]
d(8)=min {∞,d(2)+c28} = min {∞, 39+366} = 405km
d(8)=min {405,d(4)+c48} = min{ 405, 216+141 } = 357km
d(8)=min {357,d(7)+c78} = min { 357, 327+96 } = 357km*
[ 0*            39*           180*        216*         255*         316*         327*        357* ]

The shortest path problem can be solved as a minimum cost flow problem: Send unit flow from the source node s to every other node in the network[1]
##Decision variables:
xij = boolean, is there path from node i to j
cij = distance from node i to j

![alt text](https://github.com/yecicen/networkoptimizationaegea/blob/master/shortestpathmath.PNG)

![alt text](https://github.com/yecicen/networkoptimizationaegea/blob/master/map.jpg)

