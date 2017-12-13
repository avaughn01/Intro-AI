This Multi Layer Neural Network was made use Deep Learning 4 Java.  In order to test the model dl4j must be available on an IDE
I used IntelliJ to access its library.  The test wild images must also use an IDE becuase it loads a window in swing.
In the 5 ModelLoad java classes the trainLoc and testLoc strings are the locations of the training and testing datasets respectively.  By default these are empty.
The modelLoc is the location of the .zip folder of where the MLNN is saved, by default this is in the same folder as the ModelLoad java files.
I included only 1 result of testing against wild images, because the output was so long, however multiple images may be tested at the users discretion.  I will upload the wild stop sign image dataset I made and converted to: 
github.com/avaughn01/Intro-AI/project/WildImages

The firsttest.zip, modelFirstAdjust.zip, RRELU_norm_model4.zip, dropOut_RELU_model5.zip, and modelFinal.zip are the MLNN's to be loaded with the respective Java files: ModelLoadFirstTest.java, ModelLoadFirstAdjust.java, ModelLoadRRELU.java, ModelLoadDrop.java, ModelLoadFinal.java

The results from the drop out neural network were very poor, and I am not sure why, also the training for final was extremely well, but had a robust amount of overfitting for the training data.  

Overall my approach to this project was wrong.  I spent the semester creating models for networks based off of changing the dataset in an attempt to get a nice normalized dataset to input into the neural network.  I was able to accomplish a normalized dataset, but it resulted in overfitting, and extremely poor real world test results.  In the future it will be a far superior approach to adjust the configuration of the neural network itself and learn how the feautures of the neural network work, as opposed to trying to adjust the dataset itself.

Index:  0-66 Explanation of data set used, and citation
	67-126 Results of NeraulNetworks for training and testing data
	127-171 Sample testing Final Neural Network against a wild image of a stop sign
0.	Data set explanation:
1.	
2.	Format:
3.	Name of Folder : English Description
4.
5. 	0: 20 speed limit
6. 	1: 30 speed limit
7. 	2: 50 speed limit
8. 	3: 60 speed limit
9. 	4: 70 speed limit
10.	5: 80 speed limit
11. 	6: 90 speed limit
12. 	7: 100 speed limit
13. 	8: 120 speed limit
14. 	9: no passing
15. 	10: no passing for large vehicles
16. 	11: priority, you have right of way at next intersection
17. 	12: priority, you have right of way until cancelled
18. 	13: yield
19. 	14: stop
20. 	15: road closed
21. 	16: vehicles over 2.5 tons prohibited
22. 	17: do not enter
23. 	18: general danger
24. 	19: curve left
25. 	20: curve right
26. 	21: double curve, curves left first
27. 	22: rough road
28. 	23: slippery when wet or dirty
29. 	24: road narrows (right side)
30. 	25: road work
31. 	26: traffic signal ahead
32. 	27: pedestrians
33. 	28: child pedestrians
34. 	29: bicycle crossing
35. 	30: ice/snow danger
36. 	31: animal danger
37. 	32: end of all restrictions (general speed limit still applies)
38. 	33: mandatory direction of travel (right)
39. 	34: mandatory direction of travel (left)
40. 	35: mandatory direction of travel (straight)
41. 	36: mandatory direction of travel (straight or right)
42. 	37: mandatory direction of travel (straight or left)
43. 	38: pass obstacle on right side, keep right
44. 	39: pass obstacle on left side, keep left
45. 	40: yield to roundabout
46. 	41: end no passing zone (all vehicles)
47. 	42: end no passing zone (large vehicles)
48.
49.	english description obtained from
50.
51.	https://www.transchool.lee.army.mil/adso/documents/zeichen.pdf
52.	which is the pamphlet given to soldiers when they get stationed in Germany
53.	Data set explanation:
54.
55.	Citation for data set
56.
57.	J. Stallkamp, M. Schlipsing, J. Salmen, and C. Igel. The German Traffic Sign Recognition Benchmark: A multi-class classification 52.	58.	competition. In Proceedings of the IEEE International Joint Conference on Neural Networks, pages 1453–1460. 2011. 
59.
60.	@inproceedings{Stallkamp-IJCNN-2011,
61.	author = {Johannes Stallkamp and Marc Schlipsing and Jan Salmen and Christian Igel},
62.	    booktitle = {IEEE International Joint Conference on Neural Networks},
63.	    title = {The {G}erman {T}raffic {S}ign {R}ecognition {B}enchmark: A multi-class classification competition},
64.	    year = {2011},
65.	    pages = {1453--1460}
66.	}
67.	===============================================================
68.	Statistics of first Neural Network (no modifications to dataset)
69.	Training stats
70.	Accuracy: 	88.81%
71.	Precision:	90.35%
72.	Recal:		.8492
73.	F1 Score:	.8710
74.
75.	Test stats
76.	Accuracy:	68.54%
77.	Precision:	71.97%
78.	Recall:		.6322
79.	F1 Score:	.6582
80.	===============================================================
81.	Statistics of First Adjust Neural Network (adjusted about standard deviation and mean, did not remove outliers)
82.	Training Stats
83.	Accuracy:	86.78%
84.	Precision:	96.09%
85.	Recall:		.8905
86.	F1 Score:	.9161
87.
88.	Test Stats
89.	Accuracy:	48.27%
90.	Precision:	55.30%
91.	Recall:		.4524
92.	F1 Score:	.4594
93.	===============================================================
94.	Statistics of RRELU Adjust Neural Network (adjust about standard deviation, mean and using RRELU activation)
95.	Training Stats
96.	Accuracy:	76.56%
97.	Precision:	87.38%
98.	Recal:		.7089
99.	F1 Score:	.7531
100.	
101.	Test Stats
102.	Accuracy: 	58.52%
103.	Precision:	70.11%
104.	Recall:		51.33%
105.	F1 Score:	54.57%
106.	===============================================================
107.	Statistics of Drop Out Neural Network (no modifications, but used dropout)
108.	Training Stats	// Due to the significantly low statistics testing evaluation was not done
109.	Accuracy:	13.73%
110.	Precision:	22.21%
111.	Recall:		.0802
112.	F1 Score:	.1381
113.	===============================================================
114.	Statistics for final Neural Network (Adjusted dataset about standard deviation, the mean, and removed outliers)
115.	Training Stats
116.	Accuracy:	99.98%
117.	Precision:	99.99%
118.	Recal:		.9999
119.	F1 Score:	.9999
120.
121.	Test Stats
122.	Accuracy:	72.92%
123.	Precision:	72.39%
124.	Recall:		.7016
125.	F1 Score:	.7058
126.	===============================================================
127.	Sample testing of final Neural Network against wild image of a Stop Sign
128.	Probability	Label
129.	0.29  		0000
130.	0.59  		0001	
131.	0.00  		0002
132.	0.00  		0003	
133.	0.00  		0004	
134.	0.00  		0005
135.	0.00 		0006
136.	0.00  		0007
137.	0.00  		0008
138.	0.00 		0009
139.	0.00  		0010
140.	0.00  		0011
141.	0.00 		0012
142.	0.00  		0013
143.	0.00  		0014	
144.	0.00  		0015
145.	0.00  		0016
146.	0.11  		0017
147.	0.00  		0018
148.	0.00  		0019
149.	0.00  		0020
150.	0.00  		0021
151.	0.00  		0022
152.	0.00 		0023
153.	0.00  		0024
154.	0.00  		0025
155.	0.00  		0026
156.	0.00  		0027
157.	0.00  		0028
158.	0.00  		0029
159.	0.00  		0030
160.	0.00  		0031
161.	0.00  		0032
162.	0.00  		0033
163.	0.00  		0034
164.	0.00  		0035
165.	0.00  		0036
166.	0.00  		0037
167.	0.00  		0038
168.	0.00  		0039
169.	0.00  		0040
170.	0.00 		0041
171.	0.00		0042
