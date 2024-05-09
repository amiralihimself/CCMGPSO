# Cooperative Coevolutionary Multi-Guide Particle Swarm Optimization  (CCMGPSO) 
A Java implementation of CCMGPSO, an algorithm for solving large-scale multi-objective optimization problems. 
-------------------------------------------------------------------------------------------------------------------------------

This repo contains a Java implementation of our algorithm CCMGPSO, an algorithm for solving large-scale multi-objective optimization problems. This algorithm was born out of our research on large-scale multi-objective problems, and it showed competitive performance for a variety of separable and non-separable problems with many decision variables and two to three objectives. [The findings of our research were published in the Swarm and Evolutionary Computation journal in 2023](https://www.sciencedirect.com/science/article/pii/S2210650223000366).  

------------------------------------------------------


## About CCMGPSO
Many problems that are encountered in real-life applications consist of two or three conflicting objectives and many decision variables. Multi-guide particle swarm optimization (MGPSO) is a novel meta-heuristic for multi-objective optimization based on particle swarm optimization (PSO). MGPSO has been shown to be competitive when compared with other state-of-the-art multi-objective optimization algorithms for low-dimensional (and even many-objective) problems. However, a recent study has shown that MGPSO does not scale well when the number of decision variables is increased. This paper proposes a new scalable MGPSO-based algorithm, termed cooperative coevolutionary multi-guide particle swarm optimization (abbreviated as CCMGPSO), that incorporates ideas from cooperative coevolution (CC). CCMGPSO uses new techniques to spend less computational budget by periodically assigning only one CC-based subswarm to each objective (as opposed to using numerous CC-based subswarms). Results show that the proposed CCMGPSO is highly competitive for high-dimensional problems with reference to the inverted generational distance (IGD) metric.

### Accessing the Paper
A preprint of the paper is avaible on this repo. To access the preprint, please refer to `CCMGPSO/ccmgpso_manuscript.pdf`.

------------------------------------------------------
## Acknowledgements and Citations 
If you are doing research on optimization or other relevant topics and you want to use this repo to include CCMGPSO in your experimental studies, please include a link to this repo in your bibliography or somewhere in the paper (e.g., in a footnote). Furthermore, please cite CCMGPSO as: 


```
@article{madani2023cooperative,
  author       = {Amirali Madani and
                  Andries P. Engelbrecht and
                  Beatrice M. Ombuki{-}Berman},
  title        = {Cooperative coevolutionary multi-guide particle swarm optimization
                  algorithm for large-scale multi-objective optimization problems},
  journal      = {Swarm and Evolutionary Computation},
  volume       = {78},
  pages        = {101262},
  year         = {2023},
  url          = {https://doi.org/10.1016/j.swevo.2023.101262},
  doi          = {10.1016/J.SWEVO.2023.101262},
  timestamp    = {Tue, 06 Jun 2023 10:54:20 +0200},
  biburl       = {https://dblp.org/rec/journals/swevo/MadaniEO23.bib},
  bibsource    = {dblp computer science bibliography, https://dblp.org}
}
```
